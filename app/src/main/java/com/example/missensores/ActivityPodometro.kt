package com.example.missensores

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import kotlin.system.exitProcess

class ActivityPodometro : AppCompatActivity(),SensorEventListener {
    lateinit var sm:SensorManager
    lateinit var spod:Sensor
    var contador=0
    var tempCont=contador

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_podometro)
        sm=getSystemService(SENSOR_SERVICE) as SensorManager
        spod=sm.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)
        if(spod==null){
            Log.d("sensor","Error sensor no soportado")
            finish()
        }



    }

    override fun onSensorChanged(event: SensorEvent?) {
        val tvres=findViewById<TextView>(R.id.tvRes)
        if(tempCont>0)
            contador=tempCont
        if(event!!.values[0]>0){
            contador++
            tvres.setText(event!!.values[0].toString() + " pasos"+contador)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    override fun onResume() {
        super.onResume()
        spod.also { pod-> sm.registerListener(this,pod,SensorManager.SENSOR_DELAY_NORMAL) }
    }

    override fun onPause() {
        super.onPause()
        tempCont=contador
        sm.unregisterListener(this)
    }
}