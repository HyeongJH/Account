plugins {
    id("com.android.application") version "8.1.1"
    id("com.google.gms.google-services")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "kr.ac.duksung.account"
    compileSdk = 34

    defaultConfig {
        applicationId = "kr.ac.duksung.account"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-auth:22.3.0")
    implementation("com.google.firebase:firebase-database:20.3.0")
    //implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.11")
    //implementation("com.google.mlkit:vision-common:17.3.0")
    //implementation("com.google.firebase:firebase-ml-vision:24.1.0")
    implementation("androidx.camera:camera-core:1.3.0")
    implementation("com.google.firebase:firebase-storage:20.3.0")
    //implementation("com.google.mediapipe:tasks-vision:latest.release")
    //implementation("com.google.android.gms:play-services-mlkit-face-detection:17.1.0")
    //implementation("com.google.mlkit:face-mesh-detection:16.0.0-beta1")
    //implementation("gun0912.ted:tedpermission:2.0.0")
    //testImplementation("junit:junit:4.13.2")
    //androidTestImplementation("androidx.test.ext:junit:1.1.5")
    implementation("androidx.exifinterface:exifinterface:1.3.6")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //implementation ("com.github.bumptech.glide:glide:4.11.0")
    //annotationProcessor ("com.github.bumptech.glide:compiler:4.11.0")
}