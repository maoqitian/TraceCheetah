package com.mao.tracecheetahdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mao.tracelib.TraceCheetahTime

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        TraceCheetahTime.methodStart("onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        TraceCheetahTime.methodEnd("onCreate")

    }
}