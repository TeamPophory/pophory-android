plugins {
    pophory("feature")
}

android {
    namespace = "com.teampophory.pophory.data.config"
}

dependencies {
    implementation(projects.domain.config)
    implementation(projects.core.common)
    implementation(projects.core.network)

}