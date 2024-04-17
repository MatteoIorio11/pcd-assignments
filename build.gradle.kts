plugins {
    id("java")
    application
}

application {
    mainClass.set("pcd.ass01.simtrafficexamples.RunTrafficSimulationMassiveTest")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    // Use JUnit Jupiter for testing.

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // This dependency is used by the application.
    implementation("com.google.guava:guava:33.0.0-jre")
    implementation("io.vertx:vertx-core:4.5.7")
    implementation("io.vertx:vertx-web:4.5.7")
    implementation("io.vertx:vertx-web-client:4.5.7")
}

tasks.test {
    useJUnitPlatform()
}