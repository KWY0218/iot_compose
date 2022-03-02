package com.example.iot_compose.service

import android.app.Notification
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.Observer
import com.example.iot_compose.MainActivity
import com.example.iot_compose.MainViewModel
import com.example.iot_compose.R
import com.example.iot_compose.repository.RepositoryImpl
import javax.inject.Inject

// service 내에서 alarmState 를 관찰하기 위해서 LifecycleService 사용
class MainService : Service(){
    private val binder = LocalBinder()
    var setAlarm = false

    inner class LocalBinder: Binder(){
        fun getService(): MainService = this@MainService
    }

    override fun onBind(p0: Intent?): IBinder {
        return binder
    }

    // alarmState 를 관찰하다가 True 가 되면, notification 을 푸쉬하고
    // changeAlarmState 함수를 통해서 Firebase 내에 있는 pushAlarm API 상태를 False 로 바꾼다.
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        if(setAlarm){
            NotificationMessage()
            setAlarm = false
        }
        // 앱을 종료해도 서비스가 작동하도록 한다.
        return START_STICKY
    }

    // foregroundService 설정
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()
        val pendingIntent: PendingIntent =
            Intent(this, MainActivity::class.java).let { notificationIntent ->
                PendingIntent.getActivity(this, 0, notificationIntent, FLAG_IMMUTABLE)
            }

        val notification: Notification = Notification.Builder(this, getString(R.string.channel_name))
            .setContentTitle(getText(R.string.notification_title))
            .setContentText(getText(R.string.notification_message))
            .setSmallIcon(R.drawable.icon)
            .setContentIntent(pendingIntent)
            .setTicker(getText(R.string.ticker_text))
            .build()

        startForeground(1, notification)

    }

    // Notification 설정
    @RequiresApi(Build.VERSION_CODES.O)
    fun NotificationMessage(){
        val pendingIntent: PendingIntent =
            Intent(this, MainActivity::class.java).let { notificationIntent ->
                PendingIntent.getActivity(this, 0, notificationIntent, FLAG_IMMUTABLE)
            }

        val pushNotification = Notification.Builder(this, getString(R.string.channel_name))
            .setContentTitle(getText(R.string.warning_topic))
            .setContentText(getText(R.string.warning_msg))
            .setSmallIcon(R.drawable.warning)
            .setContentIntent(pendingIntent)

        with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification that you must define
            notify(R.string.channel_name, pushNotification.build())
        }
    }
}