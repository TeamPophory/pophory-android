@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("com.teampophory.pophory.feature")
    id("com.teampophory.pophory.compose")
}

android {
    namespace = "com.teampophory.pophory.common"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}
