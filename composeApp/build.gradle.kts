import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.androidx.room)
    id("com.google.gms.google-services")
}

room {
    schemaDirectory("$projectDir/schemas")
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    sourceSets {
        androidMain.dependencies {
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.lottie.compose)
            implementation(libs.androidx.core.splashscreen)
            implementation(libs.androidx.navigation.compose)
            implementation("com.microsoft.identity.client:msal:5.+")
            implementation(libs.androidx.security.crypto.v110)
            implementation(libs.androidx.material.icons.extended)
            implementation(libs.coil.compose)
            implementation("io.coil-kt:coil-video:2.7.0")
            implementation(libs.kotlinx.datetime)
            implementation(libs.compose.multiplatform.v2101)
            implementation(libs.core)
            implementation(libs.accompanist.systemuicontroller)
            implementation("com.google.firebase:firebase-messaging-ktx:24.0.0")
            implementation("androidx.work:work-runtime-ktx:2.9.0")
            implementation("com.mikepenz:multiplatform-markdown-renderer-m3:0.27.0")
            implementation(libs.androidx.camera.camera2)
            implementation(libs.androidx.camera.lifecycle)
            implementation(libs.androidx.camera.view)
            implementation(libs.mlkit.barcode.scanning)
            implementation("com.google.guava:guava:33.2.1-android")

            implementation(project.dependencies.platform("com.google.firebase:firebase-bom:34.1.0"))

            implementation("com.google.firebase:firebase-firestore")
            implementation("com.google.firebase:firebase-auth")

            implementation("androidx.media3:media3-exoplayer:1.3.1")
            implementation("androidx.media3:media3-ui:1.3.1")
        }
        commonMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(projects.shared)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()
}

android {
    namespace = "org.example.project"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "org.example.project"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    add("kspIosArm64", libs.androidx.room.compiler)
    add("kspIosSimulatorArm64", libs.androidx.room.compiler)
    debugImplementation(libs.compose.uiTooling)
}