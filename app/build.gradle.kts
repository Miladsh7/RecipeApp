plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    kotlin("kapt")
    id("kotlin-parcelize")
    id ("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.msh.recipapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.msh.recipapp"
        minSdk = 23
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    val nav_version = "2.7.7"
    val room_version = "2.6.1"

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    //Navigation Dependency
    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")

    // retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    // gson converter
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    //OkHTTP client
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    //Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    //Dagger-Hilt
    implementation("com.google.dagger:hilt-android:2.50")
    kapt("com.google.dagger:hilt-android-compiler:2.49")

    //Room Dependency
    implementation("androidx.room:room-runtime:$room_version")
    kapt("androidx.room:room-compiler:$room_version")
    implementation ("androidx.room:room-ktx:$room_version")

    //Lifecycle
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.5.1")

    //other
    implementation("com.facebook.shimmer:shimmer:0.5.0")
    implementation("com.todkars:shimmer-recyclerview:0.4.1")
    implementation("io.coil-kt:coil:2.5.0")
    implementation("de.hdodenhof:circleimageview:3.1.0")
    implementation ("com.airbnb.android:lottie:6.4.0")

    //player
    implementation("com.pierfrancescosoffritti.androidyoutubeplayer:core:12.1.0")

    // Add these to app level build.gradle (or to module that will use Kohii)
    implementation ("im.ene.kohii:kohii-core:1.4.0.2017001") // core library
    implementation ("im.ene.kohii:kohii-exoplayer:1.4.0.2017001") // default support for ExoPlayer
    implementation ("com.google.android.exoplayer:exoplayer:2.19.1") // required ExoPlayer implementation.

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}