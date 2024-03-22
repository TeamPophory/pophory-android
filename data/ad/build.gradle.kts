plugins {
    pophory("feature")
    alias(libs.plugins.sentry)
}

android {
    namespace = "com.teampophory.pophory.data.ad"
}

dependencies {
    implementation(projects.domain.ad)
    implementation(projects.core.common)
    implementation(projects.core.network)
    implementation(libs.security)
    implementation(libs.sentry)
}
