package com.example.missensores

import android.content.Context
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView

class MainActivity : AppCompatActivity(), SensorEventListener {
    lateinit var sm: SensorManager
    lateinit var sensor: Sensor


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sm=getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor=sm.getDefaultSensor(Sensor.TYPE_PROXIMITY)
        if(sensor==null){
            Log.e("sensor","sensor no soportado")
            finish()
        }
    }

    override fun onResume() {
        sensor.also { sm.registerListener(this,sensor,
            SensorManager.SENSOR_DELAY_NORMAL) }
        super.onResume()
    }

    override fun onPause() {
        sm.unregisterListener(this)
        super.onPause()
    }

    override fun onSensorChanged(event: SensorEvent?) {
        val text_view=findViewById<TextView>(R.id.id_tvSensor)
        text_view.setText(event!!.values[0].toString())
        Log.d("sensor",sensor.maximumRange.toString())
        if(event.values[0]<sensor.maximumRange)
            window.decorView.setBackgroundColor(Color.GREEN)
        else
            window.decorView.setBackgroundColor(Color.RED)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}