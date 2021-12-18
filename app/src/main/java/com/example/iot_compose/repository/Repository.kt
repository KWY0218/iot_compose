package com.example.iot_compose.repository

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.iot_compose.url.URL
import com.example.iot_compose.url.URL.AlarmState
import com.example.iot_compose.url.URL.ChangeAngleDirection
import com.example.iot_compose.url.URL.ChangeAngleState
import com.example.iot_compose.url.URL.INSTANCE
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

// 파이어베이스에 있는 데이터를 뷰모델에서 쉽게 사용하기 위해서 가공하는 역할을 한다.
class Repository {
    /*
    * db : Firebase 내에 있는 API 를 불러오는 변수
    * alarmState : outsiderDetect/pushAlarm 의 값을 저장하는 함수
    * currentAngle : motorControl/changeAngle/CurrentAngle 의 값을 저장하는 함수
    */
    private val db = FirebaseDatabase.getInstance(INSTANCE).reference
    private val _alarmState:MutableLiveData<String> = MutableLiveData()
    private val _currentAngle:MutableLiveData<Float> = MutableLiveData()
    val currentAngle : LiveData<Float>
        get() = _currentAngle
    val alarmState : LiveData<String>
        get() = _alarmState

    /*
    * Firebase 에서 데이터를 불러와서 초기값을 설정한다.
    */
    init {
        db.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                _currentAngle.value = snapshot.child(URL.CurrentAngle).value.toString().toFloat()
                _alarmState.value = snapshot.child(AlarmState).value.toString()
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
            }
        })
    }

    /*
    * changeAngle : motorControl/changeAngle/Angle 의 값을 들어온 방향값으로 바꾼 후
    *               motorControl/changeAngle/State 의 값을 "True" 로 설정한다.
    *
    * changeAlarmState : outsiderDetect/pushAlarm 의 값을 False 로 바꾼다.
    * */
    fun changeAngle(direction:String){
        db.child(ChangeAngleDirection).setValue(direction)
        db.child(ChangeAngleState).setValue("True")
    }

    fun changeAlarmState(){
        db.child(AlarmState).setValue("False")
    }
}