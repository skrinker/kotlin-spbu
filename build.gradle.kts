import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.31"
    kotlin("plugin.serialization") version "1.4.31"
    id("io.gitlab.arturbosch.detekt") version "1.15.0"
    id("org.jetbrains.dokka") version "1.4.20"
    id("org.openjfx.javafxplugin") version "0.0.9"
    application
}

group = "me.user"
version = "1.1"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation("com.google.code.gson:gson:2.8.8")
    implementation("com.squareup.okhttp3:okhttp:3.14.6")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.4.0")
    implementation("no.tornado:tornadofx:1.7.20")
    implementation("org.openjfx:javafx-base:11.0.2")
    implementation("org.openjfx:javafx:11.0.2")
    implementation("org.openjfx:javafx-controls:11.0.2")
    implementation("org.junit.jupiter:junit-jupiter:5.4.2")
    implementation("io.ktor:ktor-websockets:1.5.4")
    implementation("io.ktor:ktor-server-core:1.5.4")
    implementation("io.ktor:ktor-server-netty:1.5.4")
    implementation("io.ktor:ktor-serialization:1.5.4")
    implementation("io.ktor:ktor-client-cio:1.5.4")
    testImplementation(kotlin("test-junit"))
    implementation("io.ktor:ktor-client-websockets:1.5.4")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.1.0")
    implementation("com.charleskorn.kaml:kaml:0.28.3")
    implementation("com.squareup:kotlinpoet:1.6.0")
    implementation("org.slf4j:slf4j-log4j12:1.7.29")
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.15.0")
}

detekt {
    failFast = true // fail build on any finding
    config = files("./config/detekt/detekt.yml")
    buildUponDefaultConfig = true
}

javafx {
    version = "11"
    modules("javafx.controls")
}

tasks.test {
    useJUnitPlatform()

    testLogging {
        events(
            TestLogEvent.STANDARD_ERROR,
            TestLogEvent.STARTED,
            TestLogEvent.PASSED,
            TestLogEvent.FAILED,
            TestLogEvent.SKIPPED
        )
    }
}

tasks.withType<KotlinCompile>() {
    kotlinOptions {
        jvmTarget = "11"
        freeCompilerArgs = listOf("-Werror")
    }
}

application {
    mainClass.set("MainKt")
}
