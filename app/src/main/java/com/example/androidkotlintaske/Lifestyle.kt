package com.example.androidkotlintaske

import android.app.AliasActivity
import android.app.Service
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.*
import java.util.*
import kotlin.concurrent.fixedRateTimer
import kotlin.concurrent.scheduleAtFixedRate

class Lifestyle : Service(), SensorEventListener {

    lateinit var sensorManager: SensorManager

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        sensorManager.registerListener(
            this,
            sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
            SensorManager.SENSOR_DELAY_FASTEST
        )
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val timer = Timer("schedule", true);
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        sensorManager.unregisterListener(this)
        Log.d("Sensor: ", "DESTROYED")
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // do nothing
    }

    override fun onSensorChanged(event: SensorEvent) {
        val ox = event.values[0]
        val oy = event.values[1]
        val oz = event.values[2]

        val timer = Timer("schedule", true)

        val sp = getSharedPreferences("Colors", Context.MODE_PRIVATE)
        val editor = sp.edit()
        var red: Int = sp.getInt("red", 128)
        var green: Int = sp.getInt("red", 128)

        timer.scheduleAtFixedRate(3000L, 500L) {
            if ((ox > 10) || (ox < -10) || (oy > 10) || (oy < -10) || (oz > 10) || (oz < -11)) {
                if (red in 1..255) {
                    red -= 1
                    editor.putInt("red", red)
                }
                if (green in 1..255) {
                    green += 1
                    editor.putInt("green", green)
                }
            } else {
                if (red in 1..255) {
                    red += 1
                    editor.putInt("red", red)
                }
                if (green in 1..255) {
                    green -= 1
                    editor.putInt("green", green)
                }
            }
            Log.d("Color: ", "$red  $green")
            editor.apply()
        }
    }
}
