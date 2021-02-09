package com.mao.tracelib

/**
 *  author : maoqitian
 *  date : 2021/2/8 22:53
 *  description : 耗时统计抽象接口
 */
interface ITraceCheetah {

    object EMPTY_TRACER_CHEETAH : ITraceCheetah {
        override fun traceStart() {

        }

        override fun traceEnd() {
        }

        override fun methodStart(method: String?) {
        }

        override fun methodEnd(method: String?) {
        }

    }

    //统计开始
    fun traceStart()
    //统计结束
    fun traceEnd()
    //方法开始
    fun methodStart(method: String?)
    //方法结束
    fun methodEnd(method: String?)
}