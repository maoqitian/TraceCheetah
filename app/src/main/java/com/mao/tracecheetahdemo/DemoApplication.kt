package com.mao.tracecheetahdemo

import android.app.Application
import com.mao.tracelib.TraceCheetahTime
import com.mao.tracelib.impl.INSTANCE

/**
 * @Description:
 * @author maoqitian
 * @date 2021/3/5 0005 17:15
 */
class DemoApplication :Application() {

    override fun onCreate() {
        super.onCreate()
        println("DemoApplication onCreate 方法调用")
        // 初始化 TraceCheetahTime
        val tracer = INSTANCE
        tracer.setThreshold(1)
        TraceCheetahTime.setTracer(tracer)
    }
}