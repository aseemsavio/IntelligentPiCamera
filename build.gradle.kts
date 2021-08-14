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

    // https://mvnrepository.com/artifact/org.jetbrains.kotlinx/kotlinx-coroutines-core
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.1")

    /*// https://github.com/chen0040/java-ssd-object-detection
    implementation("com.github.chen0040:java-ssd-object-detection:1.0.1")*/

    // Tensorflow Java API
    implementation("org.tensorflow:tensorflow:1.5.0")
    implementation("org.tensorflow:proto:1.5.0")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "11"
}

val mainClass = "com.aseemsavio.intelligentpicamera.AppKt"

tasks {
    register("fatJar", Jar::class.java) {
        archiveClassifier.set("all")
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        manifest {
            attributes("Main-Class" to mainClass)
        }
        from(configurations.runtimeClasspath.get()
            .onEach { println("Adding from dependencies: ${it.name}") }
            .map { if (it.isDirectory) it else zipTree(it) })
        val sourcesMain = sourceSets.main.get()
        sourcesMain.allSource.forEach { println("Adding from sources: ${it.name}") }
        from(sourcesMain.output)
    }
}