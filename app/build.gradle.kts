plugins {
    id("com.android.application")
}

val releaseStoreFile = System.getenv("SIGNING_STORE_FILE")
val hasCiReleaseSigning = !releaseStoreFile.isNullOrBlank()

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

    buildFeatures {
        buildConfig = true
    }

    signingConfigs {
        create("ciRelease") {
            if (hasCiReleaseSigning) {
                storeFile = file(releaseStoreFile!!)
                storePassword = System.getenv("SIGNING_STORE_PASSWORD")
                keyAlias = System.getenv("SIGNING_KEY_ALIAS")
                keyPassword = System.getenv("SIGNING_KEY_PASSWORD")
            }
        }
    }

    buildTypes {
        debug {
            isDebuggable = true
        }
        release {
            isDebuggable = false
            isMinifyEnabled = false
            signingConfig = if (hasCiReleaseSigning) {
                signingConfigs.getByName("ciRelease")
            } else {
                signingConfigs.getByName("debug")
            }
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
