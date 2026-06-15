plugins {
    id("com.android.application")
}

android {
    namespace = "dev.codex.gmsunlock"
    compileSdk = 35

    defaultConfig {
        applicationId = "dev.codex.gmsunlock"
        minSdk = 27
        targetSdk = 35
        versionCode = 1
        versionName = "1.0.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    compileOnly("de.robv.android.xposed:api:82")
}
