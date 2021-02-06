package com.mao.tracecheetah.plugin

import com.android.build.gradle.AppExtension
import com.mao.tracecheetah.transform.TraceCheetahTransform
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 *  author : maoqitian
 *  date : 2021/2/6 14:45
 *  description : TraceCheetah 插件
 */
class TraceCheetahPlugin :Plugin<Project>{

    override fun apply(p0: Project) {

        println("======TraceCheetahPlugin加载===========")
        val appExtension = p0.extensions.getByType(AppExtension::class.java)
        val transform = TraceCheetahTransform()
        appExtension.registerTransform(transform)
    }
}