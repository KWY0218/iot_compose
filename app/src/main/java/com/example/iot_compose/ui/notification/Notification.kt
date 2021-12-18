package com.example.iot_compose.ui.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.iot_compose.MainViewModel
import com.example.iot_compose.R

@Composable
fun PushNotification(mainViewModel: MainViewModel){
    val alarmState: String by mainViewModel.alarmState.observeAsState("")
    if(alarmState=="True") {
        NotificationMessage()
        mainViewModel.changeAlarmState()
    }
}

// 알람 객체 생성
@Composable
private fun NotificationMessage(){
    val context = LocalContext.current
    val channelId:String = context.getString(R.string.channel_name)

    val builder = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.drawable.warning)
        .setContentTitle(context.getString(R.string.warning_topic))
        .setContentText(context.getString(R.string.warning_msg))
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)

    createNotificationChannel()

    with(NotificationManagerCompat.from(context)) {
        notify(R.string.channel_name, builder.build())
    }
}

// 시스템에 알림 채널 입력
@Composable
private fun createNotificationChannel() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val context = LocalContext.current
        val id = context.getString(R.string.channel_name)
        val descriptionText = context.getString(R.string.channel_description)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(id, descriptionText, importance).apply {
            description = descriptionText
        }
        val notificationManager: NotificationManager =
            LocalContext.current.getSystemService(Context.NOTIFICATION_SERVICE)
                    as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}
