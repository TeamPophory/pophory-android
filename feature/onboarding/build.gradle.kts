plugins {
    pophory("feature")
}

android {
    namespace = "com.teampophory.pophory.onboarding"
}

dependencies {
    implementation(projects.domain.auth)
    implementation(projects.feature.auth)
    implementation(projects.core.common)
    implementation(projects.core.network)
    implementation(projects.core.designsystem)
    implementation(libs.splash.screen)
    implementation(libs.fragment.ktx)
}
