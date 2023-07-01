@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("com.teampophory.pophory.application")
    id("com.teampophory.pophory.compose")
    alias(libs.plugins.sentry)
    alias(libs.plugins.google.services)
    alias(libs.plugins.app.distribution)
    alias(libs.plugins.crashlytics)
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
        }
    }
}

dependencies {
    implementation(project(":bottomnavigation"))
    implementation(project(":core:common"))
    implementation(libs.constraintlayout)
    implementation(libs.startup)
    implementation(libs.fragment.ktx)
    implementation(libs.security)
    implementation(libs.splash.screen)

    // Google
    implementation(libs.google.android.gms)
    implementation(libs.flexbox)

    // Third Party
    implementation(libs.dot.indicator)
    implementation(libs.coil.core)
    implementation(libs.kakao.login)
    implementation(libs.bundles.retrofit)

    debugImplementation(libs.bundles.flipper)

    //Firebase
    implementation(platform(libs.firebase))
    implementation(libs.bundles.firebase)
}
