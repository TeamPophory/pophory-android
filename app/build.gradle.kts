@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("com.teampophory.pophory.application")
    alias(libs.plugins.sentry)
    alias(libs.plugins.google.services)
}

android {
    namespace = "com.teampophory.pophory"

    defaultConfig {
        applicationId = "com.teampophory.pophory"
        versionCode = libs.versions.versionCode.get().toInt()
        versionName = libs.versions.appVersion.get()
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
    implementation(project(":bottomnavigation"))
    implementation(libs.dot.indicator)
    implementation(libs.google.android.gms)
    implementation(libs.constraintlayout)
    implementation(libs.coil.core)

    implementation(platform(libs.firebase))
    implementation(libs.bundles.firebase)
}
