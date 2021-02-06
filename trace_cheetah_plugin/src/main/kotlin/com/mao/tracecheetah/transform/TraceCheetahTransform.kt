package com.mao.tracecheetah.transform

import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.internal.pipeline.TransformManager

/**
 *  author : maoqitian
 *  date : 2021/2/6 15:04
 *  description : 插件Transform 可以被看作是 Gradle 在编译项目时的一个 task
 */
class TraceCheetahTransform :Transform(){
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

    override fun transform(transformInvocation: TransformInvocation?) {
        super.transform(transformInvocation)
    }
}