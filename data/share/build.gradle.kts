plugins {
    pophory("feature")
}

android {
    namespace = "com.teampophory.pophory.data.share"
}

dependencies {
    implementation(projects.domain.share)
    implementation(projects.core.common)
    implementation(projects.core.network)
    implementation(libs.bundles.retrofit)
}