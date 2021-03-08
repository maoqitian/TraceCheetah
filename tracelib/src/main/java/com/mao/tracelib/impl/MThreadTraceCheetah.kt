package com.mao.tracelib.impl

import android.os.Looper

/**
 * @Description: 主线方法程耗时检测
 * @author maoqitian
 * @date 2021/2/9 0009 15:57
 */
val INSTANCE: MThreadTraceCheetah = MThreadTraceCheetah()

class MThreadTraceCheetah : ThreadTraceCheetah {

    constructor() : super(Looper.getMainLooper())
    constructor(level: Int) : super(Looper.getMainLooper(), level)
}