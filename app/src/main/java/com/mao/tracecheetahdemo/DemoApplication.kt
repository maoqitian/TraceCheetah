package com.mao.tracecheetahdemo

import android.app.Application
import com.mao.tracelib.TraceCheetahTime
import com.mao.tracelib.impl.MThreadTraceCheetah

/**
 * @Description:
 * @author maoqitian
 * @date 2021/3/5 0005 17:15
 */
class DemoApplication :Application() {

    override fun onCreate() {
        super.onCreate()
        // 初始化 TraceCheetahTime
        val tracer = MThreadTraceCheetah()
        tracer.setThreshold(1)
        TraceCheetahTime.setTracer(tracer)
    }
}