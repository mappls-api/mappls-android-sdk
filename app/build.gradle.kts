import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    id("com.mappls.services.android")
}

android {
    namespace = "com.mappls.sdk.demo"
    compileSdk = 35


    defaultConfig {
        applicationId = "com.mappls.sdk.demo"
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
    buildFeatures {
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    kotlin {
        jvmToolchain(21)
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.glide)
    implementation(libs.play.services.location)
    implementation (libs.colorpicker)

    implementation(platform(libs.mappls.bom))
    implementation(libs.mappls.android.sdk)
    implementation(libs.mappls.driving.range.plugin)
    implementation(libs.mappls.scalebar.plugin)
    implementation(libs.mappls.directions.plugin)
    implementation(libs.mappls.markerview.plugin)
    implementation(libs.mappls.annotation.plugin)
    implementation(libs.mappls.place.widget)
    implementation(libs.mappls.nearby.ui)
    implementation(libs.mappls.category.search.ui)
    implementation(libs.mappls.direction.ui)
    implementation(libs.mappls.geoanalytics.plugin)
    implementation(libs.mappls.raster.catalogue.plugin)
    implementation(libs.mappls.geofence.ui)
    implementation(libs.mappls.feedback.ui)

}