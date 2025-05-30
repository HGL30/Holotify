plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
//    alias(libs.plugins.google.gms)
    id("kotlin-kapt")
    id("kotlin-parcelize")
}

android {
    namespace = "com.example.holotify"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.holotify"
        minSdk = 21
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.firebase.firestore.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation("com.cloudinary:cloudinary-android:2.3.1") // Cloudinary
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1") // ViewModel
    implementation("androidx.fragment:fragment-ktx:1.6.2")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.3.1") // LiveData
    implementation("androidx.recyclerview:recyclerview:1.2.1") // RecyclerView
    implementation("androidx.room:room-runtime:2.4.0") // SQLite Room
    implementation("androidx.room:room-ktx:2.4.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0") // Retrofit for network requests
    implementation("com.squareup.retrofit2:converter-gson:2.9.0") // Retrofit converter
    implementation("com.squareup.picasso:picasso:2.71828") // Picasso for image loading
    implementation("com.github.bumptech.glide:glide:4.15.1")
    kapt("com.github.bumptech.glide:compiler:4.15.1")
}