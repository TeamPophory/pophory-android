@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    pophory("feature")
    pophory("compose")
}

android {
    namespace = "com.teampophory.pophory.designsystem"
}

dependencies {
    implementation(libs.splash.screen)
}
