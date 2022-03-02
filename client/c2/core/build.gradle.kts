plugins {
    id("android-library-convention")
}
android {
    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Dep.AndroidX.Compose.version
    }
}


dependencies {
    implementation(project(":data"))
    // Compose
    implementation(Dep.AndroidX.Compose.ui)
    implementation(Dep.AndroidX.Compose.material)
    implementation(Dep.AndroidX.Compose.materialAdapter)
    implementation(Dep.AndroidX.Compose.tooling)
    implementation(Dep.AndroidX.Compose.activity)

    implementation(Dep.Kotlin.stdlibJvm)
    implementation(Dep.Coil.core)
    implementation(Dep.AndroidX.coreKtx)
    implementation(Dep.AndroidX.appcompat)
    implementation(Dep.AndroidX.UI.browser)
    implementation("com.google.android.material:material:1.4.0")
}