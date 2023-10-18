package com.example.missensores

import android.content.Context
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.getSystemService

class ActivityGiroscopio : AppCompatActivity() ,SensorEventListener{
    lateinit var sm:SensorManager
    lateinit var sensgiro:Sensor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_giroscopio)
        sm= getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensgiro=sm.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
        if(sensgiro==null){
            finish()
            Log.d("Sensor","Fallo sensor no soportado")
        }
    }

    override fun onSensorChanged(event: SensorEvent) {
        if(event.values[2]>0.5f){
            window.decorView.setBackgroundColor(Color.BLUE)
        }else if(event.values[2]<-0.5f)
            window.decorView.setBackgroundColor(Color.YELLOW)
    }

    override fun onResume() {
        sm.registerListener(this,sensgiro,SensorManager.SENSOR_DELAY_NORMAL)
        super.onResume()
    }

    override fun onPause() {
        sm.unregisterListener(this)
        super.onPause()
    }
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }
}