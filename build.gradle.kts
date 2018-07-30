import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    kotlin("jvm") version "1.2.51"
}

group = "com.arrowmaker.proxy"
version = "0.1.0"

repositories {
    mavenCentral()
}

dependencies {
    compile(kotlin("stdlib-jdk8"))

    // https://mvnrepository.com/artifact/io.netty/netty-all
    compile("io.netty", "netty-all", "4.1.28.Final")

    testCompile("org.junit.jupiter", "junit-jupiter-api", "5.2.0")
    testCompile("org.junit.jupiter", "junit-jupiter-api", "5.2.0")
    testRuntime("org.junit.jupiter", "junit-jupiter-api", "5.2.0")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}