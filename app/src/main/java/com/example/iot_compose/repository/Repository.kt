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

class Repository {
    private val db = FirebaseDatabase.getInstance(INSTANCE).reference

    val currentAngle : LiveData<Float>
        get() = _currentAngle
    private val _currentAngle:MutableLiveData<Float> = MutableLiveData()

    val alarmState : LiveData<String>
        get() = _alarmState
    private val _alarmState:MutableLiveData<String> = MutableLiveData()

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

    fun changeAngle(direction:String){
        db.child(ChangeAngleDirection).setValue(direction)
        db.child(ChangeAngleState).setValue("True")
    }

    fun changeAlarmState(){
        db.child(AlarmState).setValue("False")
    }



}