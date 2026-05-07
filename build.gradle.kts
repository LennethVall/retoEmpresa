plugins {
    kotlin("jvm") version "1.9.23"
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm:2.3.8")
    implementation("io.ktor:ktor-server-netty-jvm:2.3.8")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:2.3.8")
    implementation("io.ktor:ktor-serialization-jackson-jvm:2.3.8")
}

kotlin {
    jvmToolchain(21)
}

application {
    mainClass.set("ApplicationKt")
}
