plugins {
    id("toyle") version "1.0-SNAPSHOT"
    kotlin("jvm") version "1.9.0"
}

group = "com.chaottic"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    implementation("org.ow2.asm:asm:9.5")
    implementation("org.ow2.asm:asm-commons:9.5")
    implementation("org.ow2.asm:asm-util:9.5")
    implementation("org.ow2.asm:asm-tree:9.5")
    implementation("it.unimi.dsi:fastutil:8.5.12")
}

tasks.test {
    useJUnitPlatform()
}