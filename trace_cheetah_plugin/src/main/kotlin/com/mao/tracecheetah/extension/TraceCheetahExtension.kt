package com.mao.tracecheetah.extension

import com.mao.tracecheetah.Constants

/**
 *  author : maoqitian 插件扩展 可以在 gradle 中配置闭包 TraceCheetah 来设置参加相关参数
 *  date : 2021/2/17 16:34
 *  description :
 */
open class TraceCheetahExtension {

    /**
     * 是否启用
     */
    var enable = true
    /**
     * 要处理的类，正则表达式
     */
    var processClassesRegex = null
    /**
     * 插入方法开始处的代码。支持变量，详见Constants中的定义
     */
    var codeBeforeMethod = Constants.DEFAULT_METHOD_START
    /**
     * 插入方法结束处的代码。支持变量，详见Constants中的定义
     */
    var codeAfterMethod = Constants.DEFAULT_METHOD_END

    // 跳过方法
    var skipConstructor = false
    var skipStaticMethod = false
    var skipSimpleMethod = true



}