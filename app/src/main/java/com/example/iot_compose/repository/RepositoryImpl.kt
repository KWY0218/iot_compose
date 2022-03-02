package com.example.iot_compose.repository

import android.content.ContentValues
import android.util.Log
import com.example.iot_compose.state.Result
import com.example.iot_compose.url.URL
import com.example.iot_compose.url.URL.AlarmState
import com.example.iot_compose.url.URL.ChangeAngleDirection
import com.example.iot_compose.url.URL.ChangeAngleState
import com.example.iot_compose.url.URL.INSTANCE
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject
import kotlinx.coroutines.delay

// 파이어베이스에 있는 데이터를 뷰모델에서 쉽게 사용하기 위해서 가공하는 역할을 한다.
class RepositoryImpl @Inject constructor(): Repository {
    /*
    * db : Firebase 내에 있는 API 를 불러오는 변수
    */
    private val db = FirebaseDatabase.getInstance(INSTANCE).reference

    /*
    * Firebase 에서 Angle 값을 불러온다.
    */
    override suspend fun getAngle(): Result {
        var response: Result = Result.Loading
        db.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.child(URL.CurrentAngle).value.toString().toFloat()
                response = Result.Success(value)
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
                response = Result.Fail("Failed to read value. ${error.toException()}")
            }
        })
        delay(1000)
        Log.d("MainViewModel", "response: $response")
        return response
    }

    override suspend fun getAlarmState(): Result {
        var response: Result = Result.Loading
        db.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.child(AlarmState).value.toString()
                response = Result.Success(value)
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
                response = Result.Fail("Failed to read value. ${error.toException()}")
            }
        })
        delay(1000)
        Log.d("MainViewModel", "response: $response")
        return response
    }

    /*
    * changeAngle : motorControl/changeAngle/Angle 의 값을 들어온 방향값으로 바꾼 후
    *               motorControl/changeAngle/State 의 값을 "True" 로 설정한다.
    *
    * changeAlarmState : outsiderDetect/pushAlarm 의 값을 False 로 바꾼다.
    * */
    override fun changeAngle(direction:String){
        db.child(ChangeAngleDirection).setValue(direction)
        db.child(ChangeAngleState).setValue("True")
    }

    override fun changeAlarmState(){
        db.child(AlarmState).setValue("False")
    }
}