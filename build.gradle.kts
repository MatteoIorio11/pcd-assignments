plugins {
    id("java")
    application
}

application {
    mainClass.set("pcd.ass02.part1.simtrafficexamples.RunTrafficSimulationMassiveTest")
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

    //jsoup for HTML parsing
    // https://mvnrepository.com/artifact/org.jsoup/jsoup
    implementation("org.jsoup:jsoup:1.7.2")

    implementation("io.reactivex.rxjava3:rxjava:3.1.8")

}

tasks.test {
    useJUnitPlatform()
}