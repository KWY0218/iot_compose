package com.example.iot_compose

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.iot_compose.service.MainService
import com.example.iot_compose.ui.MainScreen
import com.example.iot_compose.ui.theme.Iot_composeTheme
import dagger.hilt.android.AndroidEntryPoint

// UI에 관련된 것을 다루는 역할을 한다
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var service: MainService
    private var bound = false
    private val connection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            val binder = p1 as MainService.LocalBinder
            service = binder.getService()
            bound = true
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            bound = false
        }

    }

    override fun onStart() {
        super.onStart()
        createNotificationChannel()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Intent(this, MainService::class.java).also { intent ->
                startForegroundService(intent)
                bindService(intent, connection, Context.BIND_AUTO_CREATE)
            }

        } else {
            Intent(this, MainService::class.java).also { intent ->
                startService(intent)
                bindService(intent, connection, Context.BIND_AUTO_CREATE)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Iot_composeTheme {
                if(mainViewModel.uiState.alarmState == "True") setAlarm()
                MainScreen(
                    mainViewModel = mainViewModel,
                    stopCCTVService = { stopCCTVService() }
                )
            }
        }
    }

    // Notification 채널 설정
    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(name, descriptionText, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    // 서비스를 종료하고 앱을 종료한다.
    private fun stopCCTVService() {
        stopService(Intent(this, MainService::class.java))
        unbindService(connection)
        bound = false
        finish()
    }

    private fun setAlarm(){
        service.setAlarm = true
        mainViewModel.changeAlarmState()
    }
}