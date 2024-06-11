plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.devtools.ksp")
    alias(libs.plugins.google.android.libraries.mapsplatform.secrets.gradle.plugin)
}

android {
    namespace = "com.edwinyosua.ceritaapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.edwinyosua.ceritaapp"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "BASE_URL", "\"https://story-api.dicoding.dev/v1/\"")
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
        buildConfig = true
    }
}

dependencies {

    //GSON
    implementation(libs.gson)
    //Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson.v290)
    //OkHTTP
    implementation(libs.squareup.okhttp)
    implementation(libs.com.squareup.okhttp3.logging.interceptor)
    //ViewModel
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.activity.ktx)
    //DataStore
    implementation(libs.androidx.datastore.preferences)
    //Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
    //Paging
    implementation(libs.androidx.paging.runtime)
    //Glide
    implementation(libs.glide)
    //Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.paging)
    implementation(libs.play.services.maps)
    ksp(libs.androidx.room.compiler)
    //mockito
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.inline)
    //TestDispatcher
    testImplementation(libs.kotlinx.coroutines.test.v190rc)
    //InstantTaskExecutorRule()
    testImplementation(libs.androidx.core.testing)
    androidTestImplementation(libs.androidx.core.testing)


    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.coroutines.android.v190rc)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.activity)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.core)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}