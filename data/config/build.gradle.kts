plugins {
    pophory("feature")
}

android {
    namespace = "com.teampophory.pophory.data.config"
}

dependencies {
    implementation(projects.domain.config)
    implementation(projects.core.common)
    implementation(projects.core.network)

    // Firebase
    implementation(platform(libs.firebase))
    implementation(libs.bundles.firebase)
    implementation(libs.firebase.config.ktx)
}