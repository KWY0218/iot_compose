package com.example.iot_compose.ui.button

import androidx.compose.runtime.livedata.observeAsState
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.iot_compose.MainViewModel
import com.example.iot_compose.R
import com.example.iot_compose.ui.theme.BtnColor
import com.example.iot_compose.ui.theme.Iot_composeTheme
import com.example.iot_compose.ui.theme.TextColor


// 왼쪽 버튼을 표현하는 함수
@Composable
fun LeftButton(currentAngle: Float, onClick: () -> Unit) {
    Log.d("LeftButton", currentAngle.toString())
    Button(
        contentPadding = PaddingValues(
            horizontal = 26.dp,
            vertical = 15.dp
        ),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = BtnColor
        ),
        shape = RoundedCornerShape(10.dp),
        onClick = onClick
    ) {
        Text(
            text = "LEFT",
            color = Color.White,
            fontFamily = FontFamily(Font(R.font.applesdgothicneor))
        )
    }
}

// 오른쪽 버튼을 표현하는 함수
@Composable
fun RightButton(currentAngle: Float, onClick: () -> Unit) {
    Button(
        contentPadding = PaddingValues(
            horizontal = 26.dp,
            vertical = 15.dp
        ),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = BtnColor
        ),
        shape = RoundedCornerShape(10.dp),
        onClick = onClick
    ) {
        Text(
            text = "RIGHT",
            color = Color.White,
            fontFamily = FontFamily(Font(R.font.applesdgothicneor))
        )
    }
}

// 메인 레이아웃에 보여 줄 버튼들을 표시 할 함수
@Composable
fun MainButton(mainViewModel: MainViewModel) {
    val context = LocalContext.current
    val currentAngle: Float by mainViewModel.currentAngle.observeAsState(0F)
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues = PaddingValues(bottom = 50.dp)),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.Bottom
    ) {
        LeftButton(
            currentAngle = currentAngle,
            onClick = {
                if(currentAngle>2.5) mainViewModel.changeAngle("Left")
                else Toast.makeText(context,"최대 각도 입니다.",Toast.LENGTH_SHORT).show()
            }
        )
        RightButton(
            currentAngle = currentAngle,
            onClick = {
                if(currentAngle<12.5) mainViewModel.changeAngle("Right")
                else Toast.makeText(context,"최대 각도 입니다.",Toast.LENGTH_SHORT).show()
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    Iot_composeTheme {

    }
}