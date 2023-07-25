import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.0"
    `maven-publish`
    `java-gradle-plugin`
}

group = "com.chaottic"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "19"
}

gradlePlugin {
    plugins {
        create("toyle") {
            id = "toyle"
            implementationClass = "com.chaottic.toyle.ToylePlugin"
        }
    }
}

publishing {
    repositories {
        mavenLocal()
    }
    publications {
        create<MavenPublication>("maven") {
            groupId = project.group as String
            artifactId = "toyle"
            version = project.version as String
            from(components["java"])
        }
    }
}

tasks.build {
    finalizedBy(tasks.publishToMavenLocal)
}