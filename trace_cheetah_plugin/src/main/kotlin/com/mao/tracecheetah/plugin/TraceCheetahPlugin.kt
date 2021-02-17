package com.mao.tracecheetah.plugin

import com.android.build.gradle.AppExtension
import com.mao.tracecheetah.extension.TraceCheetahExtension
import com.mao.tracecheetah.transform.TraceCheetahTransform
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 *  author : maoqitian
 *  date : 2021/2/6 14:45
 *  description : TraceCheetah 插件
 */
class TraceCheetahPlugin :Plugin<Project>{

    override fun apply(project: Project) {

        println("======TraceCheetahPlugin加载===========")
        //自定义插件中，对当前project进行扩展 使用 TraceCheetah 闭包
        // TraceCheetahExtension 必须使用 open 关键字修饰 不然会出现 class final 问题 插件引入出错
        //参考地址 https://docs.gradle.org/current/userguide/custom_plugins.html
        project.extensions.create("TraceCheetah",TraceCheetahExtension::class.java)
        val appExtension = project.extensions.findByType(AppExtension::class.java)
        val transform = TraceCheetahTransform(project)
        appExtension?.registerTransform(transform)
    }
}