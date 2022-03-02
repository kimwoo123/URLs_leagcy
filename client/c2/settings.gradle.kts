pluginManagement {
    val agpVersion = "7.0.3"
    val kotlinVersion = "1.5.32"
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    plugins {
        id("com.android.application") version agpVersion
        id("com.android.library") version agpVersion
        id("org.jetbrains.kotlin.android") version kotlinVersion
        id("org.jetbrains.kotlin.jvm") version kotlinVersion
        id("org.jetbrains.kotlin.kapt") version kotlinVersion
        id("com.google.devtools.ksp") version "${kotlinVersion}-1.0.0"
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://plugins.gradle.org/m2/") {
            content {
                includeGroup("org.jlleitschuh.gradle")
            }
        }
        maven("https://plugins.gradle.org/m2/")
        maven("https://jitpack.io")
        maven("https://devrepo.kakao.com/nexus/content/groups/public/")
    }
}
rootProject.name = "free"
include(
    ":app",
    ":core",
    ":data",
    ":domain"
)
