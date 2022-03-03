package com.example.iot_compose

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.iot_compose.service.MainService
import com.example.iot_compose.ui.MainScreen
import com.example.iot_compose.ui.MainViewModel
import com.example.iot_compose.ui.theme.Iot_composeTheme
import dagger.hilt.android.AndroidEntryPoint

// UI에 관련된 것을 다루는 역할을 한다
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    override fun onStart() {
        super.onStart()
        createNotificationChannel()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Intent(this, MainService::class.java).also { intent ->
                startForegroundService(intent)
            }

        } else {
            Intent(this, MainService::class.java).also { intent ->
                startService(intent)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Iot_composeTheme {
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
        finish()
    }
}