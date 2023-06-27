@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("com.teampophory.pophory.application")
    alias(libs.plugins.sentry)
}

android {
    namespace = "com.teampophory.pophory"

    defaultConfig {
        applicationId = "com.teampophory.pophory"
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
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
    implementation(libs.dot.indicator)
    implementation(libs.google.android.gms)
    implementation(libs.constraintlayout)
    implementation(libs.coil.core)
}
