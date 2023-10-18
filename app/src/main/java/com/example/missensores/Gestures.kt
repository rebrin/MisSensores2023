package com.example.missensores

import android.gesture.Gesture
import android.gesture.GestureLibraries
import android.gesture.GestureLibrary
import android.gesture.GestureOverlayView
import android.gesture.Prediction
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView

class Gestures : AppCompatActivity(),GestureOverlayView.OnGesturePerformedListener {
    lateinit var gestureLibrary: GestureLibrary
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gestures)
        iniciar()
    }

    fun iniciar(){
        var overlayView=findViewById<GestureOverlayView>(R.id.gestureOverlayView)
        overlayView.addOnGesturePerformedListener(this)
        gestureLibrary=GestureLibraries.fromRawResource(this,R.raw.gesture)
        if(!gestureLibrary.load()){ //si no puede cargar el modelo entrenado
            finish()
        }
    }

    override fun onGesturePerformed(overlay: GestureOverlayView?, gesture: Gesture?) {

        var prediccionesCalc = gestureLibrary.recognize(gesture)
        var tvPrediciones=findViewById<TextView>(R.id.tvPredicciones)
        tvPrediciones.text=""
        prediccionesCalc.forEach{
            tvPrediciones.append("${it.name}:${it.score}\n")
            Log.d("predicciones","${it.name}:${it.score}")
            }

    }
}