plugins {
    pophory("feature")
}

android {
    namespace = "com.teampophory.pophory.feature.share"

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    // core module
    implementation(projects.core.common)
    implementation(projects.core.network)
    implementation(projects.core.designsystem)

    // domain module
    implementation(projects.domain.share)

    // Firebase
    implementation(platform(libs.firebase))
    implementation(libs.bundles.firebase)

    // android
    implementation(libs.fragment.ktx)

    // Third Party
    implementation(libs.coil.core)
    implementation(libs.retrofit)
}
