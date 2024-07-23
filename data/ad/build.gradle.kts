plugins {
    pophory("feature")
}

android {
    namespace = "com.teampophory.pophory.data.ad"
}

dependencies {
    implementation(projects.domain.ad)
    implementation(projects.core.common)
    implementation(projects.core.network)
    implementation(libs.security)
}
