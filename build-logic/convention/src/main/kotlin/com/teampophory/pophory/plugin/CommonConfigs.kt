package com.teampophory.pophory.plugin

import com.android.build.gradle.BaseExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import java.util.Properties

internal fun Project.configureAndroidCommonPlugin() {
    val properties = Properties().apply {
        load(rootProject.file("local.properties").inputStream())
    }

    apply<AndroidKotlinPlugin>()
    apply<KotlinSerializationPlugin>()
    with(plugins) {
        apply("kotlin-kapt")
        apply("kotlin-parcelize")
    }
    apply<AndroidHiltPlugin>()

    extensions.getByType<BaseExtension>().apply {
        defaultConfig {
//            val apiKey = properties["apiKey"] as? String ?: ""
//            val dataStoreKey = properties["dataStoreKey"] as? String ?: ""
//            val devUrl = properties["devApi"] as? String ?: ""
//            val baseUrl = properties["newApi"] as? String ?: ""
            manifestPlaceholders["sentryDsn"] = properties["sentryDsn"] as String
            manifestPlaceholders["kakaoApiKey"] = properties["kakaoApiKey"] as String
        }
        buildFeatures.apply {
            viewBinding = true
            buildConfig = true
        }
    }

    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
    dependencies {
        "implementation"(libs.findLibrary("core.ktx").get())
        "implementation"(libs.findLibrary("appcompat").get())
        "implementation"(libs.findLibrary("lifecycle.viewmodel").get())
        "implementation"(libs.findLibrary("material").get())
    }
}
