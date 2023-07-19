package com.teampophory.pophory.plugin

import com.android.build.gradle.BaseExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class ComposePlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
        extensions.getByType<BaseExtension>().apply {
            buildFeatures.apply {
                compose = true
            }
            composeOptions {
                kotlinCompilerExtensionVersion = libs.findVersion("compose.compiler").get().requiredVersion
            }
        }

        dependencies {
            "implementation"(platform(libs.findLibrary("compose.bom").get()))
            "implementation"(libs.findBundle("compose").get())
        }
    }
}