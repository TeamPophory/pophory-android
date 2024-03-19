plugins {
    pophory("feature")
    alias(libs.plugins.sentry)
}

android {
    namespace = "com.teampophory.pophory.network"
}

dependencies {
    implementation(projects.core.common)
    implementation(libs.security)
    implementation(platform(libs.okhttp.bom))
    implementation(libs.okhttp.logging.interceptor)
    implementation(libs.kakao.login)
    implementation(libs.process.phoenix)
    debugImplementation(libs.bundles.flipper)
}
