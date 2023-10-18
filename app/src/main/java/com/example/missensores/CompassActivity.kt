package com.example.missensores

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import kotlin.math.round
import kotlin.math.roundToInt

class CompassActivity : AppCompatActivity(),SensorEventListener {
    lateinit var sensorManager: SensorManager
    lateinit var sensor: Sensor
    lateinit var iv_needle:ImageView
    var GradosActuales=0f
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compass)
        sensorManager=getSystemService(SENSOR_SERVICE) as SensorManager
        sensor=sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION)
        if(sensor==null){
            finish()
        }
        iv_needle=findViewById<ImageView>(R.id.iv_needle)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        var Grados:Float= round( event!!.values[0])
        var rotar=RotateAnimation(GradosActuales
            ,-Grados.toFloat()
            ,Animation.RELATIVE_TO_SELF
            ,0.5f
            ,Animation.RELATIVE_TO_SELF,
            0.5f)
        rotar.duration=1000
        rotar.fillAfter=true
        iv_needle.animation=rotar
        GradosActuales=Grados
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    override fun onResume() {
        super.onResume()
        sensor.also {
            sensorManager.registerListener(this,sensor,SensorManager.SENSOR_DELAY_FASTEST)
         }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }
}
