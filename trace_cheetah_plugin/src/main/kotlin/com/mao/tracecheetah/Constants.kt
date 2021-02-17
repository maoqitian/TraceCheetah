package com.mao.tracecheetah

/**
 * @Description: 常量类
 * @author maoqitian
 * @date 2021/02/17 0012 15:47
 */
object Constants {


    var VAR_CLASS_NAME = "<class-name>"
    var VAR_SIMPLE_CLASS_NAME = "<simple-class-name>"
    var VAR_METHOD_NAME = "<method-name>"

    var TRACER_PACKAGE = "com.mao.tracelib"
    //开始方法路径
    var DEFAULT_METHOD_START =
        "com.mao.tracerlib.TracerCheetahTime.methodStart(\"<class-name>.<method-name>\");"
    //结束方法路径
    var DEFAULT_METHOD_END =
        "com.mao.tracerlib.TracerCheetahTime.methodEnd(\"<class-name>.<method-name>\");"

}