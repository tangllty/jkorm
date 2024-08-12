plugins {
    kotlin("jvm")
}

group = "com.tang"
version = "1.0-SNAPSHOT"

repositories {
    gradlePluginPortal()
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}