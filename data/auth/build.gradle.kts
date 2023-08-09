@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    pophory("feature")
}

android {
    namespace = "com.teampophory.pophory.data.auth"
}

dependencies {
    implementation(project(":domain:auth"))
    implementation(project(":core:common"))
    implementation(libs.security)
    implementation(libs.bundles.retrofit)
    implementation(libs.process.phoenix)
}
