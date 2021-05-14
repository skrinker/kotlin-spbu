import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.31"
    kotlin("plugin.serialization") version "1.4.31"
    id("org.openjfx.javafxplugin") version "0.0.9"
    id("io.gitlab.arturbosch.detekt") version "1.15.0"
    id("org.jetbrains.dokka") version "1.4.20"
    application
}

group = "me.user"
version = "1.1"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.4.0")
    implementation("no.tornado:tornadofx:1.7.20")
    implementation("org.junit.jupiter:junit-jupiter:5.4.2")
    implementation("junit:junit:4.12")
    testImplementation(kotlin("test-junit"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.3")
    implementation("no.tornado:tornadofx:1.7.20")
    implementation("org.openjfx:javafx-base:11.0.2")
    implementation("org.openjfx:javafx:11.0.2")
    implementation("org.openjfx:javafx-controls:11.0.2")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.1.0")
    implementation("com.charleskorn.kaml:kaml:0.28.3")
    implementation("com.squareup:kotlinpoet:1.6.0")
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

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "11"
        freeCompilerArgs = listOf("-Werror")
    }
}

application {
    mainClass.set("MainKt")
}
