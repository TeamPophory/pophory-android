@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    pophory("feature")
    pophory("compose")
}

android {
    namespace = "com.teampophory.pophory.feature.setting"

}

dependencies {
    // core module
    implementation(projects.core.common)
    implementation(projects.core.designsystem)

    // domain module
    implementation(projects.domain.auth)

    // feature module
    implementation(projects.feature.auth)

    implementation(libs.accompanist.webview)
    implementation(libs.process.phoenix)
}
