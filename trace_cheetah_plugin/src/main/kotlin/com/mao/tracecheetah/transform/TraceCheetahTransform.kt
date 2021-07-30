package com.mao.tracecheetah.transform

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.mao.tracecheetah.extension.TraceCheetahExtension
import com.mao.tracecheetah.visitor.TraceCheetahASMClassVisitor
import org.gradle.api.Project
import org.gradle.internal.impldep.org.apache.commons.io.FileUtils
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter
import java.io.FileOutputStream

/**
 *  author : maoqitian
 *  date : 2021/2/6 15:04
 *  description : 插件Transform 可以被看作是 Gradle 在编译项目时的一个 task
 */
class TraceCheetahTransform(
    private val project: Project
) :Transform(){

    //闭包扩展
    private var traceCheetahExtension: TraceCheetahExtension? = null

    /**
     * 设置我们自定义的 Transform 对应的 Task 名称。Gradle 在编译的时候，会将这个名称显示在控制台上
     * @return String
     */
    override fun getName(): String = "TraceCheetahTransform"

    /**
     * 在项目中会有各种各样格式的文件，该方法可以设置 Transform 接收的文件类型
     * 具体取值范围
     * CONTENT_CLASS  .class 文件
     * CONTENT_JARS  jar 包
     * CONTENT_RESOURCES  资源 包含 java 文件
     * CONTENT_NATIVE_LIBS native lib
     * CONTENT_DEX dex 文件
     * CONTENT_DEX_WITH_RESOURCES  dex 文件
     * @return
     */
    override fun getInputTypes(): MutableSet<QualifiedContent.ContentType> = TransformManager.CONTENT_CLASS
    /**
     * 表示当前 Transform 是否支持增量编译 返回 true 标识支持
     * @return Boolean
     */
    override fun isIncremental(): Boolean = false
    /**
     * 定义 Transform 检索的范围
     * PROJECT 只检索项目内容
     * SUB_PROJECTS 只检索子项目内容
     * EXTERNAL_LIBRARIES 只有外部库
     * TESTED_CODE 由当前变量测试的代码，包括依赖项
     * PROVIDED_ONLY 仅提供的本地或远程依赖项
     * @return
     */
    //检索项目全部内容
    override fun getScopes(): MutableSet<in QualifiedContent.Scope> =TransformManager.SCOPE_FULL_PROJECT

    override fun transform(transformInvocation: TransformInvocation) {
        println("======Transform 方法执行===========")

        traceCheetahExtension = project.extensions.findByName("TraceCheetah") as TraceCheetahExtension?

        println("traceCheetahExtension = ${traceCheetahExtension?.processClassesRegex}," +
                "${traceCheetahExtension?.codeBeforeMethod} , ${traceCheetahExtension?.codeAfterMethod}")

        //获取所有 输入 文件集合
        val transformInputs = transformInvocation.inputs
        //输出提供者
        val transformOutputProvider = transformInvocation.outputProvider

        transformOutputProvider?.deleteAll()


        transformInputs.forEach { transformInput ->
            // Caused by: java.lang.ClassNotFoundException: Didn't find class "androidx.appcompat.R$drawable" on path 问题
            // gradle 3.6.0以上R类不会转为.class文件而会转成jar，因此在Transform实现中需要单独拷贝，TransformInvocation.inputs.jarInputs
            // jar 文件处理
            /**
             * jarInputs：各个依赖所编译成的 jar ⽂件
             * des : /app/build/intermediates/transforms/TraceCheetahTransform/....
             */
            transformInput.jarInputs.forEach {jarInput->
                val file = jarInput.file
                val des = transformOutputProvider.getContentLocation(jarInput.name,jarInput.contentTypes,jarInput.scopes,Format.JAR)
                println("jar input:${file.name} , dest: $des")
                val dest = transformOutputProvider.getContentLocation(jarInput.name,jarInput.contentTypes,jarInput.scopes,Format.JAR)
                //文件复制
                FileUtils.copyFile(file,dest)
            }

            //源码文件class 处理
            //directoryInputs代表着以源码方式参与项目编译的所有目录结构及其目录下的源码文件
            transformInput.directoryInputs.forEach { directoryInput: DirectoryInput ->
                /**
                 * derectoryInputs：本地 project 编译成的多个 class ⽂件存放的⽬
                 * des: /app/build/intermediates/transforms/TraceCheetahTransform/....
                 */
                val des = transformOutputProvider.getContentLocation(directoryInput.name,directoryInput.contentTypes,directoryInput.scopes,Format.JAR)
                println("Dir : ${directoryInput.file} ,dest: $des")
                directoryInput.file.walkTopDown()
                       .filter { it.isFile }
                       .filter { it.extension == "class" }
                       .forEach { file ->
                           if (traceCheetahExtension == null || !traceCheetahExtension!!.enable ||
                               traceCheetahExtension!!.codeAfterMethod.isEmpty() && traceCheetahExtension!!.codeBeforeMethod.isEmpty()){
                               println("plugin disable")
                           }else{
                               println("find class file:${file.name}")
                               //字节码插桩处理操作
                               var classReader = ClassReader(file.readBytes())
                               //ClassWriter.COMPUTE_MAXS 自动合并
                               var classWriter = ClassWriter(classReader,ClassWriter.COMPUTE_MAXS)
                               //2.class 读取传入 ASM visitor
                               var traceCheetahASMClassVisitor = TraceCheetahASMClassVisitor(classWriter)
                               //3.通过ClassVisitor api 处理后接收对应字节码
                               //ClassReader.EXPAND_FRAMES 设置ASM就会自动计算插桩后本地变量表和操作数栈
                               classReader.accept(traceCheetahASMClassVisitor,ClassReader.EXPAND_FRAMES)
                               //4.处理修改成功的字节码，读取字节数组
                               val bytes = classWriter.toByteArray()
                               //写回文件中
                               val fos =  FileOutputStream(file.path)
                               fos.write(bytes)
                               fos.close()
                           }
                       }
                val dest = transformOutputProvider.getContentLocation(directoryInput.name,directoryInput.contentTypes,directoryInput.scopes, Format.DIRECTORY)
                //文件复制
                FileUtils.copyDirectory(directoryInput.file,dest)
            }
        }

    }
}