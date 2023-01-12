
plugins {
    `java-library`
    `maven-publish`
    kotlin("jvm") version "1.8.0"
}

repositories {
    mavenLocal()
    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web:2.7.7")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.7.7")
    implementation("org.springframework.boot:spring-boot-starter-security:2.7.7")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.14.1")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.8.0")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.hibernate:hibernate-core:5.6.14.Final")
    implementation("org.hibernate:hibernate-entitymanager:5.6.14.Final")
    implementation("org.hsqldb:hsqldb:2.7.1")
    implementation("io.jsonwebtoken:jjwt:0.9.1")

}

group = "com.matera.restserver"
version = "0.0.1-SNAPSHOT"
description = "Rest Server"
java.sourceCompatibility = JavaVersion.VERSION_11

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}

tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
}

tasks.withType<Javadoc>() {
    options.encoding = "UTF-8"
}