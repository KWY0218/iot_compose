package com.example.iot_compose

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.iot_compose.repository.Repository
import com.example.iot_compose.service.MainService
import com.example.iot_compose.ui.button.MainButton
import com.example.iot_compose.ui.theme.Iot_composeTheme
import com.example.iot_compose.ui.webview.WebStream

// UI에 관련된 것을 다루는 역할을 한다
class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory(Repository())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Iot_composeTheme {
                startService(Intent(this, MainService::class.java))
                createNotificationChannel()
                Total()
            }
        }
    }

    // Title() : 상단 Text 를 표현한다.
    @Composable
    fun Total() {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = LocalContext.current.getString(R.string.app_name)
                        )
                    },
                    actions = {
                        IconButton(
                            onClick = {
                                stopCCTVService()
                            },
                        ) {
                            Icon(
                                Icons.Filled.ExitToApp,
                                contentDescription = null
                            )
                        }
                    }
                )
            }
        ){
            MiddleContent(mainViewModel)
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
    private fun stopCCTVService(){
        stopService(Intent(this, MainService::class.java))
        finish()
    }
}

/*
    WebStream() : 스트리밍을 위한 웹뷰를 표현한다.
    MainButton(mainViewModel) : left, right 버튼을 표현한다.
*/
@Composable
fun MiddleContent(mainViewModel: MainViewModel) {
    WebStream()
    MainButton(mainViewModel)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Iot_composeTheme {
    }
}
