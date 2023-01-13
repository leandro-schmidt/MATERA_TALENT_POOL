
plugins {
    `java-library`
    `maven-publish`
    kotlin("jvm") version "1.8.0"
    id("org.jlleitschuh.gradle.ktlint") version "11.0.0"
    id("io.gitlab.arturbosch.detekt") version "1.21.0"
    jacoco
}

repositories {
    mavenLocal()
    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }
    mavenCentral()
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
    testImplementation("org.junit.vintage:junit-vintage-engine:5.8.2")
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
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

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<Javadoc> {
    options.encoding = "UTF-8"
}

ktlint {
    version.set("0.40.0")
    verbose.set(true)
    outputToConsole.set(true)
    coloredOutput.set(true)
    reporters {
        reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.JSON)
    }
}

jacoco {
    toolVersion = "0.8.8"
}

tasks.withType<Test> {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    reports.xml.required.set(false)
    reports.csv.required.set(false)
    reports.html.outputLocation.set(file("${rootProject.rootDir}/${rootProject.name}/jacocoHtmlReport"))
}

tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            limit {
                counter = "LINE"
                value = "COVEREDRATIO"
                minimum = "0.90".toBigDecimal()
            }
        }
    }
}

tasks.check {
    dependsOn("jacocoTestCoverageVerification")
}
