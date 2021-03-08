package com.mao.tracecheetahdemo

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.mao.tracelib.TraceCheetahTime
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var mSecondsRemain = 0

    private val mHandler: Handler = Handler()

    private val mTickRunnable: Runnable = object : Runnable {
        override fun run() {
            if (mSecondsRemain <= 0) {
                TraceCheetahTime.traceStop()
                start.isEnabled = true
                start.text = "Trace 10s"
            } else {
                start.text = "Tracing($mSecondsRemain)"
                mHandler.postDelayed(this, 1000)
                mSecondsRemain--
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Thread.sleep(2000)

        start.setOnClickListener {
            start.isEnabled = false
            start.text = "Tracing"
            mSecondsRemain = 10;
            TraceCheetahTime.traceStart()
            mHandler.post(mTickRunnable)
        }

    }
}