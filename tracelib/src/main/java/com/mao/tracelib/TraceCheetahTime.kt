package com.mao.tracelib

import android.os.Handler
import android.os.Looper


/**
 *  author : maoqitian
 *  date : 2021/2/8 23:20
 *  description : 提供静态调用 ITraceCheetah 空实例的方法
 */
open class TraceCheetahTime{

    companion object{

        @JvmStatic
        val HANDLER = Handler(Looper.getMainLooper())

        //开始 trace
        @JvmStatic
        fun startTrace(ms:Long,onCompleteRunnable:Runnable){
            //如果是 UI 线程直接调用
            if(Looper.getMainLooper() == Looper.myLooper()){
                traceStart()
            }else{
                HANDLER.post {
                    traceStart()
                }
            }
            //延迟结束
            HANDLER.postDelayed({
                traceStop()
                if (onCompleteRunnable !=null){
                    onCompleteRunnable.run()
                }
            },ms)
        }

        private var TRACER: ITraceCheetah = ITraceCheetah.EMPTY_TRACER_CHEETAH

        fun getTracer(): ITraceCheetah? {
            return TRACER
        }

        fun setTracer(tracer: ITraceCheetah?) {
            TRACER = tracer ?: ITraceCheetah.EMPTY_TRACER_CHEETAH
        }

        @JvmStatic
        fun traceStart() {
           TRACER.traceStart()
        }
        @JvmStatic

        fun traceStop() {
           TRACER.traceEnd()
        }
        @JvmStatic

        fun methodStart(method: String?) {
           TRACER.methodStart(method)
        }
        @JvmStatic
        fun methodEnd(method: String?) {
            TRACER.methodEnd(method)
        }


    }
}