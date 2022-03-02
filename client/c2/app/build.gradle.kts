import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("android-application-convention")
    id("android-compose-convention")
    kotlin("kapt")
    id("com.google.firebase.crashlytics")
    id("dagger.hilt.android.plugin")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id("com.google.android.gms.oss-licenses-plugin")
}

val key: String = gradleLocalProperties(rootDir).getProperty("KAKAO")


android {
    defaultConfig {
        applicationId = "com.keelim.free"
        versionCode = 1
        versionName = "0.0.1"
        vectorDrawables.useSupportLibrary = true
    }

    buildTypes {
        debug {
            buildConfigField("String", "key", key)
        }

        release {
            isMinifyEnabled = BuildTypeRelease.isMinifyEnabled
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
        compose = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(Dep.Kotlin.stdlibJvm)

    implementation(Dep.AndroidX.activity.ktx)
    implementation(Dep.AndroidX.lifecycle.viewModelKtx)
    implementation(Dep.AndroidX.lifecycle.runtime)

    // Android UI
    implementation(Dep.AndroidX.UI.browser)
    implementation(Dep.AndroidX.UI.material)

    // Compose
    implementation(Dep.AndroidX.Compose.ui)
    implementation(Dep.AndroidX.Compose.material)
    implementation(Dep.AndroidX.Compose.tooling)

    // OkHttp
    implementation(Dep.OkHttp.loggingInterceptor)

    // Hilt
    implementation(Dep.Dagger.Hilt.android)
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    kapt(Dep.Dagger.Hilt.compiler)

    implementation(Dep.AndroidX.appcompat)
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.3.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1")

    // kotlin
    implementation(Dep.Kotlin.coroutines.core)
    implementation(Dep.Kotlin.coroutines.android)

    implementation(Dep.timber)
    implementation(Dep.Coil.core)


    testImplementation(Dep.Test.junit)
    testImplementation(Dep.Test.assertJ)
    testImplementation(Dep.Test.mockito)

    // startup
    implementation(Dep.AndroidX.startup)
    // firebase

    implementation(platform("com.google.firebase:firebase-bom:28.4.1"))
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-crashlytics")
    implementation("com.google.firebase:firebase-messaging")
    implementation("com.google.android.gms:play-services-auth:19.2.0")

    implementation(Dep.AndroidX.datastore.datastore)
    implementation(Dep.AndroidX.datastore.preferences)

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:0.20.0")

    implementation("androidx.navigation:navigation-fragment-ktx:2.3.5")
    implementation("androidx.navigation:navigation-ui-ktx:2.3.5")

    implementation("com.firebaseui:firebase-ui-auth:7.2.0")

    implementation("com.google.android.gms:play-services-oss-licenses:17.0.0")
    implementation("androidx.biometric:biometric:1.0.1")

    implementation("com.github.fornewid:neumorphism:0.3.0")
    implementation("org.jsoup:jsoup:1.13.1")

    implementation("kr.co.prnd:persistBottomSheetFragment:1.0.0")
    implementation("io.coil-kt:coil-gif:1.4.0")
}

kapt {
    useBuildCache = true
}
