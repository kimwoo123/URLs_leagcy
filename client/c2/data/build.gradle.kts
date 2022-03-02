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

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.6.0")
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("com.google.android.material:material:1.4.0")

    implementation(Dep.Dagger.Hilt.android)
    kapt(Dep.Dagger.Hilt.compiler)

    // kotlin
    implementation(Dep.Kotlin.coroutines.core)
    implementation(Dep.Kotlin.coroutines.android)

    implementation(Dep.timber)

    testImplementation(Dep.Test.junit)
    testImplementation(Dep.Test.assertJ)
    testImplementation(Dep.Test.mockito)

    implementation(Dep.SquareUp.core)
    implementation(Dep.SquareUp.loggingInterceptor)
    implementation(Dep.SquareUp.urlconnection)
    implementation(Dep.SquareUp.retrofit)
    implementation(Dep.SquareUp.retrofit_gson)
    implementation(Dep.SquareUp.retrofit_moshi)
    implementation(Dep.moshi)
    implementation(Dep.moshi_kotlin)
    implementation(Dep.moshi_codegen)

    implementation("com.mocklets:pluto:1.1.1")

    implementation(platform("com.google.firebase:firebase-bom:28.4.1"))
    implementation("com.google.firebase:firebase-auth-ktx")

}

kapt {
    useBuildCache = true
}