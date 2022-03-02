plugins {
    id("android-library-convention")
    id("dagger.hilt.android.plugin")
    kotlin("kapt")
    id("org.jetbrains.kotlin.android")
}

android {

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    useLibrary("android.test.mock")
}


dependencies {
    implementation(project(":data"))
    implementation(Dep.Dagger.Hilt.android)
    kapt(Dep.Dagger.Hilt.compiler)

    // kotlin
    implementation(Dep.Kotlin.coroutines.core)
    implementation(Dep.Kotlin.coroutines.android)

    implementation(Dep.timber)

    testImplementation(Dep.Test.junit)
    testImplementation(Dep.Test.assertJ)
    testImplementation(Dep.Test.mockito)
}
