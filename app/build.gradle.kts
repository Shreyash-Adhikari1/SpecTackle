plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.gms.google.services)

}
    android {
        namespace = "com.example.spectackle"
        compileSdk = 35

        buildFeatures {
            viewBinding = true
        }

        defaultConfig {
            applicationId = "com.example.spectackle"
            minSdk = 24
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
            sourceCompatibility = JavaVersion.VERSION_11
            targetCompatibility = JavaVersion.VERSION_11
        }
        kotlinOptions {
            jvmTarget = "11"
        }
    }

    dependencies {

        implementation(libs.androidx.core.ktx)
        implementation(libs.androidx.appcompat)
        implementation(libs.material)
        implementation(libs.androidx.activity)
        implementation(libs.androidx.constraintlayout)
        implementation(libs.firebase.auth)
        implementation(libs.firebase.database)
        testImplementation(libs.junit)
        androidTestImplementation(libs.androidx.junit)
        androidTestImplementation(libs.androidx.espresso.core)

        implementation ("androidx.activity:activity-ktx:1.10.0") // For by viewModels
        implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.7") // For ViewModel
        implementation ("com.github.bumptech.glide:glide:4.15.1")

        //This dependency is for replacing entire fragments off of components inside a fragment
        implementation ("androidx.fragment:fragment-ktx:1.6.2")

        //This dependency is for cloudinary
        implementation("com.cloudinary:cloudinary-android:2.1.0")

        //These Dependencies are for Glide {bring image from cloudinary to display in application}
        //Not using Picasso because Glide is faster and more efficient
        implementation ("com.github.bumptech.glide:glide:4.16.0")
        annotationProcessor ("com.github.bumptech.glide:compiler:4.16.0")

    }
