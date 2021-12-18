package com.example.iot_compose.url

// URL 들을 저장하는 객체
object URL {
    const val INSTANCE = "https://iotcomposeproject-default-rtdb.asia-southeast1.firebasedatabase.app"
    const val CurrentAngle = "motorControl/changeAngle/CurrentAngle"
    const val ChangeAngleState = "motorControl/changeAngle/State"
    const val ChangeAngleDirection = "motorControl/changeAngle/Angle"
    const val AlarmState = "outsiderDetect/pushAlarm"
    const val StreamUrl = "http://1.247.223.47:5000/?action=stream"
}