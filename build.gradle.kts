plugins {
    kotlin("js") version "1.8.10"
}

group = "me.propo"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation(kotlin("stdlib-js"))
}

kotlin {
    js {
        binaries.executable()
        browser {
            commonWebpackConfig {
                cssSupport {
                    enabled.set(true)
                }
            }
        }
    }
}