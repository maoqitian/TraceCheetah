package com.mao.tracecheetah.visitor

import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.commons.AdviceAdapter

/**
 * @Description:  AdviceAdapter 是 MethodVisitor 的子类，可以更方便进行插桩操作
 * @author maoqitian
 * @date 2021/3/3 0003 16:42
 */
class TraceCheetahASMMethodVisitor(private val methodVisitor:MethodVisitor,
                                   access:Int, private val methodName:String, desc:String):
    AdviceAdapter(Opcodes.ASM5, methodVisitor,access,methodName,desc) {


    override fun visitCode() {
        super.visitCode()
        println("方法名称 $methodName")

    }

    //进入方法 方法开始插入耗时统计开始
    override fun onMethodEnter() {
        super.onMethodEnter()
        //方法参数
        mv.visitLdcInsn(methodName)
        // INVOKESTATIC 调用静态方法
        mv.visitMethodInsn(INVOKESTATIC, "com/mao/tracelib/TraceCheetahTime", "methodStart", "(Ljava/lang/String;)V", false)

    }
    //方法结束 方法结束插入耗时统计结束
    override fun onMethodExit(opcode: Int) {
        super.onMethodExit(opcode)
        mv.visitLdcInsn(methodName)
        mv.visitMethodInsn(INVOKESTATIC, "com/mao/tracelib/TraceCheetahTime", "methodEnd", "(Ljava/lang/String;)V", false)
    }

    override fun visitEnd() {
        super.visitEnd()
    }
}