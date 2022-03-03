package com.example.iot_compose.service

import android.app.Notification
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import com.example.iot_compose.MainActivity
import com.example.iot_compose.R
import com.example.iot_compose.state.Result
import com.example.iot_compose.usecase.ChangeAlarmStateUseCase
import com.example.iot_compose.usecase.GetAlarmStateUseCase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

// service 내에서 alarmState 를 관찰하기 위해서 LifecycleService 사용
@AndroidEntryPoint
class MainService : LifecycleService() {
    @Inject
    lateinit var getAlarmStateUseCase: GetAlarmStateUseCase

    @Inject
    lateinit var changeAlarmStateUseCase: ChangeAlarmStateUseCase

    lateinit var alarmState:StateFlow<Result>

    /*
        alarmState 를 관찰하다가 True 가 되면, notification 을 푸쉬하고
        changeAlarmState 함수를 통해서 Firebase 내에 있는 pushAlarm API 상태를 False 로 바꾼다.

        coldStream 인 Flow 를 hotStream 인 stateFlow 로 바꾼 후,
        파이어베이스 alarmState 의 값이 바뀔 때에만 데이터를 받게 만든다.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        lifecycleScope.launch {
            alarmState = getAlarmStateUseCase().stateIn(
                initialValue = Result.Loading,
                started = SharingStarted.WhileSubscribed(0),
                scope = lifecycleScope
            )
            alarmState.collectLatest {
                when (it) {
                    is Result.Success<*> -> {
                        val data = it.data as String
                        if (data == "True") {
                            NotificationMessage()
                            changeAlarmStateUseCase()
                        }
                    }
                    is Result.Fail<*> -> Log.d("MainService", "${it.data}")
                }
            }
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
                PendingIntent.getActivity(this, 100, notificationIntent, FLAG_IMMUTABLE)
            }

        val notification: Notification =
            Notification.Builder(this, getString(R.string.channel_name))
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
    fun NotificationMessage() {
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