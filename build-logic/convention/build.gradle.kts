plugins {
    `kotlin-dsl`
}

group = "com.teampophory.pophory.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    compileOnly(libs.agp)
    compileOnly(libs.kotlin.gradleplugin)
}

gradlePlugin {
    plugins {
        create("android-application") {
            id = "com.teampophory.pophory.application"
            implementationClass = "com.teampophory.pophory.plugin.AndroidApplicationPlugin"
        }
        create("android-feature") {
            id = "com.teampophory.pophory.feature"
            implementationClass = "com.teampophory.pophory.plugin.AndroidFeaturePlugin"
        }
        create("android-kotlin") {
            id = "com.teampophory.pophory.kotlin"
            implementationClass = "com.teampophory.pophory.plugin.AndroidKotlinPlugin"
        }
        create("android-hilt") {
            id = "com.teampophory.pophory.hilt"
            implementationClass = "com.teampophory.pophory.plugin.AndroidHiltPlugin"
        }
        create("kotlin-serialization") {
            id = "com.teampophory.pophory.serialization"
            implementationClass = "com.teampophory.pophory.plugin.KotlinSerializationPlugin"
        }
        create("junit5") {
            id = "com.teampophory.pophory.junit5"
            implementationClass = "com.teampophory.pophory.plugin.JUnit5Plugin"
        }
        create("android-test") {
            id = "com.teampophory.pophory.test"
            implementationClass = "com.teampophory.pophory.plugin.AndroidTestPlugin"
        }
        create("compose") {
            id = "com.teampophory.pophory.compose"
            implementationClass = "com.teampophory.pophory.plugin.ComposePlugin"
        }
    }
}
