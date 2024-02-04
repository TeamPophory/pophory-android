plugins {
    pophory("feature")
}

android {
    namespace = "com.teampophory.pophory.bottomnavigation"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
}
