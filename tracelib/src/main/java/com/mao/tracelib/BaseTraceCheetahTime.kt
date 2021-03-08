package com.mao.tracelib

import android.util.Log
import java.util.*
import kotlin.collections.ArrayList

/**
 * @Description: 耗时计算的基本实现 抽象类
 * @author maoqitian
 * @date 2021/2/9 0009 10:24
 */
abstract class BaseTraceCheetahTime :ITraceCheetah{

    /**
     * 日志 tag
     */
    val TAG = javaClass.simpleName

    /**
     * 调用时间。直接用数组实现栈
     */
    protected lateinit var mTimes: LongArray

    /**
     * 方法名。直接用数组实现栈
     */
    protected lateinit var mNames: ArrayList<String>

    /**
     * 最大调用嵌套层级，也就是栈的容量。调用嵌套深度不能超过此数值
     */
    private var mMaxLevel = 0

    /**
     * 当前Level，相当于栈的指针
     */
    protected var mLevel = 0

    /**
     * 方法执行超过多少 ms输出Log
     */
    protected var mThreshold = 1

    /**
     * 是否启动
     */
    protected var mEnable = false


    constructor():this(40)

    constructor(maxLevel: Int){
        mMaxLevel = maxLevel
        mTimes = LongArray(mMaxLevel)
        mNames = ArrayList(mMaxLevel)
    }


    /**
     * 方法执行超过多少 ms输出Log
     */
    fun setThreshold(threshold:Int){
        mThreshold = threshold
    }

    //重置层级
    private fun reset(){
        mLevel = 0
    }

    override fun traceStart() {
        reset()
        mEnable = true
    }

    override fun traceEnd() {
        mEnable = false
    }

    //方法开始
    override fun methodStart(method: String?) {
        if(!mEnable || !checkMatchStart(method)) return

        if(mLevel >= mMaxLevel){
            //超过最大层级
            errLog("max level exceeded, maxLevel = $mMaxLevel")
            return
        }
        //记录当前时间戳 方法名
        mTimes[mLevel] = timestamp()
        mNames.add(method!!)

        ++mLevel
    }

    //方法结束
    override fun methodEnd(method: String?) {
        if(!mEnable || mLevel <= 0 || !checkMatchEnd(method)) return

        //获取当前方法名称
        val methodName = mNames[mLevel -1]
        if (!methodIsEqual(methodName,method)){
            return
        }

        --mLevel
        //计算耗时
        val time = timestamp() - mTimes[mLevel]
        println("当前 $method 方法耗时 $time")
        //日志打印
        outputPrintLog(method,mLevel,time)
    }

    protected open fun outputPrintLog(method: String?, level: Int, time: Long) {
       if (time > mThreshold){
           logPrint("%s%s: %d ms", space(level), method, time)
       }
    }

    //空格填充
    private fun space(level: Int): String? {
        if (level <= 0) return ""
        val spaceChars = CharArray(level)
        Arrays.fill(spaceChars, '\t')
        return String(spaceChars)
    }

    //基础日志打印 可变参数 vararg 相当于将数组 args 拆分为参数，指向需要使用 * 符合
    protected open fun logPrint(msg: String, vararg args: Any?) {
        Log.w(TAG, String.format(msg, *args))
    }

    //方法是否一样
    private fun methodIsEqual(methodName: String?, method: String?): Boolean {
        if (methodName == null || method == null) return false
        val length: Int = methodName.length
        if (length != method.length) {
            return false
        }
        for (i in length - 1 downTo 0) {
            if (methodName[i] != method[i]) {
                return false
            }
        }
        return true
    }

    //错误日志打印
    private fun errLog(msg: String, vararg args: Any?) {
        Log.e(TAG, String.format(msg, *args))
    }


    //抽象方法
    /**
     * 检查匹配开始结束
     */
    protected abstract fun checkMatchStart(method: String?): Boolean
    protected abstract fun checkMatchEnd(method: String?): Boolean
    /**
     * 获取时间戳
     */
    protected abstract fun timestamp(): Long


}