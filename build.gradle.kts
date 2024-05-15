/*
 * This file was generated by the Gradle 'init' task.
 *
 * This is a general purpose Gradle build.
 * To learn more about Gradle by exploring our Samples at https://docs.gradle.org/8.6/samples
 */

plugins {
    java
    application
}

repositories {
    mavenCentral()
    maven {
        setUrl("https://repo.akka.io/maven")
    }
}

application {
    mainClass.set("pcd.ass03.part1.simtrafficexamples.RunTrafficSimulationMassiveTest")
}

val version = "2.13"

dependencies {
    implementation("org.slf4j:slf4j-api:2.0.13")
    implementation(platform("com.typesafe.akka:akka-bom_$version:2.9.3"))
    implementation("com.typesafe.akka:akka-actor-typed_$version")
    testImplementation("com.typesafe.akka:akka-actor-testkit-typed_$version")
}
