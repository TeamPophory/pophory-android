import java.util.Properties

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    pophory("application")
    pophory("compose")
    alias(libs.plugins.sentry)
    alias(libs.plugins.google.services)
    alias(libs.plugins.app.distribution)
    alias(libs.plugins.crashlytics)
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

    buildFeatures {
        dataBinding = true
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
    implementation(project(":bottomnavigation"))
    implementation(project(":core:common"))
    implementation(libs.constraintlayout)
    implementation(libs.coil.core)
    implementation(libs.flexbox)
    debugImplementation(libs.bundles.flipper)
    implementation(libs.kakao.login)
    implementation(libs.startup)
    implementation(libs.fragment.ktx)
    implementation(libs.security)
    implementation(libs.splash.screen)
    implementation(libs.exifInterface)

    // Google
    implementation(libs.google.android.gms)
    implementation(libs.flexbox)
    implementation(libs.accompanist.webview)

    // Third Party
    implementation(libs.dot.indicator)
    implementation(libs.coil.core)
    implementation(libs.kakao.login)
    implementation(libs.bundles.retrofit)
    implementation(libs.process.phoenix)

    debugImplementation(libs.bundles.flipper)

    //Firebase
    implementation(platform(libs.firebase))
    implementation(libs.bundles.firebase)
}
