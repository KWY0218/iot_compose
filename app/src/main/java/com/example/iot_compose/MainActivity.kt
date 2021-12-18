package com.example.iot_compose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.iot_compose.repository.Repository
import com.example.iot_compose.ui.button.MainButton
import com.example.iot_compose.ui.notification.PushNotification
import com.example.iot_compose.ui.theme.Iot_composeTheme
import com.example.iot_compose.ui.theme.TextColor
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
                Total(mainViewModel)
            }
        }
    }
}

/*
*   Title() : 상단 Text 를 표현한다.
    WebStream() : 스트리밍을 위한 웹뷰를 표현한다.
    MainButton(mainViewModel) : left, right 버튼을 표현한다.
    PushNotification(mainViewModel) : 외부인이 감지되면 알람을 울리는 함수
*/
@Composable
fun Total(mainViewModel: MainViewModel) {
    Title()
    WebStream()
    MainButton(mainViewModel)
    PushNotification(mainViewModel)
}

// Title() : 상단 Text 를 표현한다.
@Composable
fun Title(){
    Text(
        text = LocalContext.current.getString(R.string.app_name),
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 20.dp),
        fontWeight = FontWeight.Bold,
        color = TextColor,
        fontSize = 25.sp,
        textAlign = TextAlign.Center,
        fontFamily = FontFamily(Font(R.font.applesdgothicneor))
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Iot_composeTheme {

    }
}
