import java.util.Properties

plugins {
    pophory("application")
    pophory("compose")
    pophory("test")
    alias(libs.plugins.sentry)
    alias(libs.plugins.google.services)
    alias(libs.plugins.app.distribution)
    alias(libs.plugins.crashlytics)
    id("com.google.android.gms.oss-licenses-plugin")
}

val properties = Properties().apply {
    load(rootProject.file("local.properties").inputStream())
}

android {
    namespace = "com.teampophory.pophory"

    defaultConfig {
        applicationId = "com.teampophory.pophory"
        versionCode = libs.versions.versionCode.get().toInt()
        versionName = libs.versions.appVersion.get()
    }

    signingConfigs {
        getByName("debug") {
            keyAlias = "androiddebugkey"
            keyPassword = "android"
            storeFile = File("${project.rootDir.absolutePath}/keystore/debug.keystore")
            storePassword = "android"
        }
        create("release") {
            keyAlias = properties.getProperty("keyAlias")
            keyPassword = properties.getProperty("keyPassword")
            storeFile = File("${project.rootDir.absolutePath}/keystore/key.jks")
            storePassword = properties.getProperty("storePassword")
        }
    }

    buildTypes {
        debug {
            firebaseAppDistribution {
                artifactType = "APK"
                releaseNotesFile = "firebase/releaseNote.txt"
                groupsFile = "firebase/testers.txt"
            }
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }
}

dependencies {
    implementation(projects.bottomnavigation)

    // domain
    implementation(projects.domain.auth)
    implementation(projects.domain.share)
    implementation(projects.domain.ad)

    // data
    implementation(projects.data.auth)
    implementation(projects.data.share)
    implementation(projects.data.ad)

    // core
    implementation(projects.core.common)
    implementation(projects.core.network)
    implementation(projects.core.designsystem)

    // feature
    implementation(projects.feature.auth)
    implementation(projects.feature.setting)
    implementation(projects.feature.share)
    implementation(projects.feature.onboarding)

    implementation(libs.constraintlayout)
    implementation(libs.flexbox)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation(libs.startup)
    implementation(libs.fragment.ktx)
    implementation(libs.security)
    implementation(libs.splash.screen)
    implementation(libs.exifInterface)

    // Google
    implementation(libs.google.android.gms)
    implementation(libs.flexbox)
    implementation(libs.accompanist.webview)
    implementation(libs.oss)

    // Third Party
    implementation(libs.dot.indicator)
    implementation(libs.coil.core)
    implementation(libs.bundles.retrofit)
    implementation(libs.kakao.login)
    implementation(libs.zxing.android.embedded)

    debugImplementation(libs.bundles.flipper)

    // Firebase
    implementation(platform(libs.firebase))
    implementation(libs.bundles.firebase)
}
