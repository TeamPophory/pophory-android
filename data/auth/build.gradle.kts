@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    pophory("feature")
    alias(libs.plugins.sentry)
}

android {
    namespace = "com.teampophory.pophory.data.auth"
}

dependencies {
    implementation(projects.domain.auth)
    implementation(projects.core.common)
    implementation(projects.core.network)
    implementation(libs.security)
    implementation(libs.bundles.retrofit)
    implementation(libs.process.phoenix)
    implementation(libs.sentry)
}
