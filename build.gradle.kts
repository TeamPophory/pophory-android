import com.diffplug.gradle.spotless.SpotlessExtension

buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }

    dependencies {
        classpath(libs.kotlin.gradleplugin)
        classpath(libs.hilt.plugin)
        classpath(libs.agp)
        classpath(libs.ktlint)
        classpath(libs.oss.plugin)
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.kapt) apply false
    alias(libs.plugins.dagger.hilt) apply false
    alias(libs.plugins.ktlint) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.kotlinx.serialization) apply false
    alias(libs.plugins.sentry) apply false
    alias(libs.plugins.junit5) apply false
    alias(libs.plugins.google.services) apply false
    alias(libs.plugins.app.distribution) apply false
    alias(libs.plugins.crashlytics) apply false
    alias(libs.plugins.spotless) apply false
    alias(libs.plugins.compose.compiler) apply false
}

subprojects {
    apply(plugin = rootProject.libs.plugins.spotless.get().pluginId)

    extensions.configure<SpotlessExtension> {
        kotlin {
            target("**/*.kt")
            targetExclude("${layout.buildDirectory}/**/*.kt")
            trimTrailingWhitespace()
            endWithNewline()
            ktlint("0.49.1")
                .editorConfigOverride(
                    mapOf(
                        "charset" to "utf-8",
                        "end_of_line" to "lf",
                        "indent_style" to "space",
                        "indent_size" to 4,
                        "max_line_length" to 180,
                        "trim_trailing_whitespace" to true,
                        "ktlint_standard_filename" to "disabled",
                        "ktlint_standard_function_start_of_body_spacing" to "disabled",
                    )
                )
        }
        format("kts") {
            target("**/*.kts")
            targetExclude("${layout.buildDirectory}/**/*.kts")
        }
        format("xml") {
            target("**/*.xml")
            targetExclude("**/build/**/*.xml")
        }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.layout.buildDirectory)
}
