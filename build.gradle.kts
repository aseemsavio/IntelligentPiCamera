import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.10"
}

group = "com.aseemsavio"
version = "0.0"

repositories {
    mavenCentral()
}

dependencies {

    // https://github.com/chen0040/java-ssd-object-detection
    implementation("com.github.chen0040:java-ssd-object-detection:1.0.1")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "11"
}