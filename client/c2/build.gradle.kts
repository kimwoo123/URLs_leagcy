buildscript {
    val compose_version by extra("1.0.0")
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }

    dependencies {
        classpath("com.android.tools.build:gradle:7.0.3")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.21")
        classpath("com.google.gms:google-services:4.3.10")
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.7.1")
        classpath("com.diffplug.spotless:spotless-plugin-gradle:5.17.0")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.39.1")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.3.5")
        classpath("com.google.android.gms:oss-licenses-plugin:0.10.4")
    }
}

task("clean", Delete::class) {
    delete(rootProject.buildDir)
}