plugins {
    pophory("feature")
}

android {
    namespace = "com.teampophory.pophory.feature.auth"
}

dependencies {
    implementation(projects.domain.auth)
    implementation(projects.core.common)

    implementation(libs.kakao.login)
}