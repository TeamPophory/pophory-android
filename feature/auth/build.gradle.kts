plugins {
    pophory("feature")
}

android {
    namespace = "com.teampophory.pophory.feature.auth"
}

dependencies {
    implementation(projects.domain.auth)
    implementation(projects.core.common)
    implementation(projects.data.auth)
    implementation(projects.core.designsystem)

    implementation(libs.kakao.login)
    implementation(libs.fragment.ktx)
    implementation(libs.navigation.ui)
    implementation(libs.navigation.fragment)
}
