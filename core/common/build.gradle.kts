plugins {
    pophory("feature")
    pophory("compose")
}

android {
    namespace = "com.teampophory.pophory.common"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    implementation(libs.fragment.ktx)
    implementation(libs.retrofit)
}
