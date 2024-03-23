plugins {
    pophory("feature")
    pophory("test")
}

android {
    namespace = "com.teampophory.pophory.feature.auth"
}

dependencies {
    implementation(projects.domain.auth)
    implementation(projects.core.common)
    implementation(projects.core.designsystem)

    implementation(libs.kakao.login)
    implementation(libs.fragment.ktx)
    implementation(libs.navigation.ui)
    implementation(libs.navigation.fragment)
}
