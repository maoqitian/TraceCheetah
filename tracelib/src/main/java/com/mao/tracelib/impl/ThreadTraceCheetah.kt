package com.mao.tracelib.impl

import android.os.Looper
import android.os.SystemClock
import com.mao.tracelib.BaseTraceCheetahTime


/**
 * @Description: 对应线程 方法程耗时检测
 * @author maoqitian
 * @date 2021/2/9 0009 15:57
 */
open class ThreadTraceCheetah :BaseTraceCheetahTime{


    private lateinit var mLooper: Looper


    constructor(looper: Looper):super(){
        mLooper = looper
    }

    constructor(looper: Looper, level:Int):super(){
        mLooper = looper
    }

    override fun checkMatchStart(method: String?): Boolean {
        return Looper.myLooper() == mLooper
    }

    override fun checkMatchEnd(method: String?): Boolean {
        return Looper.myLooper() == mLooper
    }

    override fun timestamp(): Long {
        return SystemClock.currentThreadTimeMillis()
    }


}