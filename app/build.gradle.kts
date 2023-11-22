plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
   // id ("kotlin-kapt")
    id("com.google.devtools.ksp")
}


//apply(from = "libraries.gradle.kts")

android {
    namespace = "com.aps.movie"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.aps.movie"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }


    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
    packaging {
        resources {
            excludes += setOf("/META-INF/{AL2.0,LGPL2.1}")
        }
    }

}

dependencies {

    val coilVersion = "2.5.0"
    val pagingVersion = "3.2.1"
    val okHttpBomVersion = "4.12.0"
    val retrofitVersion = "2.9.0"
    val moshiAdapterVersion = "1.14.0"
    val hiltVersion = "2.48"
    val room_version = "2.6.0"

    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.4")
   // implementation (project(":shared"))

    //implementation(project.dependencies.coil)

    implementation ("io.coil-kt:coil-compose:$coilVersion")
    implementation ("androidx.paging:paging-compose:$pagingVersion")
    implementation (platform("com.squareup.okhttp3:okhttp-bom:$okHttpBomVersion"))
    implementation ("com.squareup.okhttp3:okhttp:$okHttpBomVersion")
    implementation ("com.squareup.okhttp3:logging-interceptor:$okHttpBomVersion")
    implementation ("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")
    implementation ("com.squareup.retrofit2:converter-moshi:$retrofitVersion")
    implementation ("com.squareup.moshi:moshi-adapters:$moshiAdapterVersion")
    implementation ("com.squareup.moshi:moshi-kotlin:$moshiAdapterVersion")
    implementation ("com.squareup.moshi:moshi-kotlin-codegen:$moshiAdapterVersion")
   // implementation ("com.google.dagger:hilt-android:$hiltVersion")
   // implementation ("com.google.dagger:hilt-android-compiler:$hiltVersion")

    implementation("com.google.dagger:hilt-android:2.48")
    ksp("com.google.dagger:hilt-compiler:2.48")

    ksp("com.squareup.moshi:moshi-kotlin-codegen:1.14.0")
  //  ksp("com.google.dagger:hilt-android-compiler:2.48")

    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")
    implementation("androidx.navigation:navigation-compose:2.7.5")

    // room
    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")
    // optional - Paging 3 Integration
    implementation("androidx.room:room-paging:$room_version")
    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$room_version")
    ksp("androidx.room:room-compiler:$room_version")

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.0")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}

