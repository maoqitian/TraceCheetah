package com.mao.tracecheetah.visitor

import com.mao.tracecheetah.Constants
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

/**
 * @Description: ClassVisitor
 * @author maoqitian
 * @date 2021/3/2 0002 17:55
 */
class TraceCheetahASMClassVisitor(classVisitor: ClassVisitor) : ClassVisitor(Opcodes.ASM5, classVisitor) {

    //类名
    private lateinit var className:String
    //父类名
    private var superName:String? = null

    override fun visit(
        version: Int,
        access: Int,
        name: String,
        signature: String?,
        superName: String?,
        interfaces: Array<out String>?
    ) {
        super.visit(version, access, name, signature, superName, interfaces)
        this.className = name
        this.superName = superName
    }


    override fun visitMethod(
        access: Int,
        name: String,
        descriptor: String?,
        signature: String?,
        exceptions: Array<out String>?
    ): MethodVisitor {
        val methodVisitor = cv.visitMethod(access,name,descriptor,signature,exceptions)
        //过滤不处理的类
        if (isIgnoreClass(className)) {
            println(" isIgnoreClass 类名名称 $className 跳过")
            return methodVisitor
        }

        println("类名名称 $className")
        return TraceCheetahASMMethodVisitor(methodVisitor, className ,name)
    }

    override fun visitEnd() {
        super.visitEnd()
    }


    private fun isIgnoreClass(className:String) : Boolean {
        return className.endsWith("BuildConfig") || className.startsWith(Constants.TRACER_PACKAGE)
    }

}