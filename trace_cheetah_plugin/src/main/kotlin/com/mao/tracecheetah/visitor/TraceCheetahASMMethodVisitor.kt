package com.mao.tracecheetah.visitor

import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

/**
 * @Description:
 * @author maoqitian
 * @date 2021/3/3 0003 16:42
 */
class TraceCheetahASMMethodVisitor(private val methodVisitor:MethodVisitor, private val className:String,private val methodName:String):
    MethodVisitor(Opcodes.ASM5, methodVisitor) {


    override fun visitCode() {
        super.visitCode()
        println("方法名称 $methodName")



    }

    override fun visitEnd() {
        super.visitEnd()
    }
}