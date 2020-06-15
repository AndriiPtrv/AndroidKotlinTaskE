package com.example.androidkotlintaske

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sp = getSharedPreferences("Colors", Context.MODE_PRIVATE)
        var red = java.lang.Integer.toHexString(sp.getInt("red", 128))
        var green = java.lang.Integer.toHexString(sp.getInt("green", 128))
        val blue = "00"
        color_lifestyle.setBackgroundColor(Color.parseColor("#$red$green$blue"))


        start.setOnClickListener {
            Log.d(
                "Service: ",
                "START -------------------------------------------------------------"
            )
            startService(Intent(this, Lifestyle::class.java))
//            info_container.visibility = View.VISIBLE
//            start.visibility = View.GONE
        }

        stop.setOnClickListener {
            stopService(Intent(this, Lifestyle::class.java))
            Log.d("Service: ", "STOP -------------------------------------------------------------")
//            info_container.visibility = View.GONE
//            start.visibility = View.VISIBLE
        }

        color_lifestyle.setOnClickListener {
            red = java.lang.Integer.toHexString(sp.getInt("red", 128))
            green = java.lang.Integer.toHexString(sp.getInt("green", 128))
            color_lifestyle.setBackgroundColor(Color.parseColor("#$red$green$blue"))
        }

    }
}
