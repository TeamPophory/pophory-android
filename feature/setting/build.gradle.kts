@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    pophory("feature")
    pophory("compose")
}

android {
    namespace = "com.teampophory.pophory.feature.setting"

}

dependencies {
    implementation(projects.domain.auth)
}
