package com.example.iot_compose.ui

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.iot_compose.MainViewModel
import com.example.iot_compose.R
import com.example.iot_compose.ui.button.MainButton
import com.example.iot_compose.ui.webview.WebStream

@Composable
fun MainScreen(
    stopCCTVService: ()->Unit,
    mainViewModel: MainViewModel
) {
    Total(
        mainViewModel = mainViewModel,
        stopCCTVService = { stopCCTVService() }
    )
}

// Title() : 상단 Text 를 표현한다.
@Composable
fun Total(
    mainViewModel: MainViewModel,
    stopCCTVService: ()->Unit
) {
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

/*
    WebStream() : 스트리밍을 위한 웹뷰를 표현한다.
    MainButton(mainViewModel) : left, right 버튼을 표현한다.
*/
@Composable
fun MiddleContent(mainViewModel: MainViewModel) {
    WebStream()
    MainButton(mainViewModel)
}