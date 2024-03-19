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
    implementation(libs.process.phoenix)
    implementation(libs.sentry)
}
