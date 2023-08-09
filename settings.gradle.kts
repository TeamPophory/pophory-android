pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven { setUrl("https://devrepo.kakao.com/nexus/content/groups/public/") }
    }
}

rootProject.name = "pophory"
include(":app")
include(":bottomnavigation")
include(":core:common")
include(":core:designsystem")
include(":domain:auth")
