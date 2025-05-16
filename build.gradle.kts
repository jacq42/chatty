import io.gitlab.arturbosch.detekt.Detekt
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.time.LocalDate
import java.time.format.DateTimeFormatter

buildscript {
    extra["springCloudVersion"] = "2024.0.1"

    //extra["json-path.version"] = "2.9.0"
    extra["archunitVersion"] = "1.4.1"
    extra["jvmTargetVersion"] = "21"
    extra["kotlinVersion"] = "2.1.0"
    extra["springBootVersion"] = "3.4.1"
    extra["detektVersion"] = "1.23.8"

}

plugins {
    val kotlinPluginVersion = "2.1.21"
    val springBootPluginVersion = "3.4.5"
    val detektPluginVersion = "1.23.8"

    idea
    id("org.springframework.boot") version springBootPluginVersion
    id("io.spring.dependency-management") version "1.1.7"
    id("io.gitlab.arturbosch.detekt") version detektPluginVersion
    //id("info.solidsoft.pitest") version "1.15.0"

    kotlin("jvm") version kotlinPluginVersion
    kotlin("plugin.spring") version kotlinPluginVersion
}

group = "de.jkrech.tutorials.chatty"
version = fullyQualifiedVersion()

java {
    toolchain {
        val jvmTargetVersion: String by project
        languageVersion = JavaLanguageVersion.of(jvmTargetVersion)
    }
}

tasks.withType<KotlinCompile> {
    compilerOptions {
        val jvmTargetVersion: String by project
        jvmTarget.set(JvmTarget.fromTarget(jvmTargetVersion))
        freeCompilerArgs.add("-Xjsr305=strict")
    }
}

detekt {
    source.from(files("buildSrc/src", "src"))
    config.from(files("$projectDir/etc/detekt/detekt.yml"))
    autoCorrect = true
}

tasks.withType<Detekt> {
    exclude("**/resources/**", "**/build/**")
    reports {
        html {
            required.set(true)
            outputLocation.set(file("$projectDir/build/reports/detekt/detekt.html"))
        }
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }

    all {
        exclude(group = "org.springframework.boot", module = "spring-boot-starter-tomcat")
        exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
        exclude(group = "org.springframework.boot", module = "logback-classic")
        exclude(group = "ch.qos.logback")
    }
}

allprojects {
    repositories {
        mavenCentral()
    }

    tasks.register<DependencyReportTask>("allDeps") {}
}

dependencyManagement {
    configurations.matching { it.name == "detekt" }.all {
        resolutionStrategy.eachDependency {
            if (requested.group == "org.jetbrains.kotlin") {
                useVersion(io.gitlab.arturbosch.detekt.getSupportedKotlinVersion())
            }
        }
    }
}

dependencies {
    val springCloudVersion: String by project
    val archunitVersion: String by project
    val detektVersion: String by project

    implementation("co.elastic.logging:log4j2-ecs-layout:1.7.0")

    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:$detektVersion")

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation(platform("org.springframework.cloud:spring-cloud-dependencies:${springCloudVersion}"))
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-cache")
    implementation("org.springframework.hateoas:spring-hateoas")

    implementation("org.springframework:spring-aspects")
    implementation("org.springframework.security:spring-security-oauth2-resource-server")
    implementation("org.springframework.security:spring-security-oauth2-jose")
    implementation("org.springframework.cloud:spring-cloud-starter-bootstrap")
    implementation("io.github.resilience4j:resilience4j-spring-boot3")
    implementation("io.github.resilience4j:resilience4j-reactor")
    implementation("io.github.resilience4j:resilience4j-micrometer")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("software.amazon.awssdk:s3:2.31.43")

    implementation("org.javamoney:moneta:1.4.5")

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.mockito")
    }
    testImplementation("org.junit.platform:junit-platform-suite-engine")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("com.ninja-squad:springmockk:4.0.2")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("io.mockk:mockk:1.14.2")
    testImplementation("io.kotest:kotest-assertions-core-jvm:5.9.1")
    testImplementation("com.atlassian.oai:swagger-request-validator-restassured:2.44.1")

    testImplementation("com.tngtech.archunit:archunit:$archunitVersion")
    testImplementation("com.tngtech.archunit:archunit-junit5-api:$archunitVersion")
    testImplementation("com.lemonappdev:konsist:0.17.3")
    testImplementation("au.com.dius.pact:consumer:4.6.17")
    testImplementation("au.com.dius.pact.consumer:junit5:4.6.17")
    testImplementation("org.springframework.cloud:spring-cloud-contract-wiremock")
    testRuntimeOnly("com.tngtech.archunit:archunit-junit5:$archunitVersion")
    testImplementation("io.github.microcks:microcks-util:1.11.2")

}

dependencyManagement {
    dependencies {
        // REMOVE when spring-reactor has a netty version > 1.1.12
        dependency("io.netty:netty-codec-http:4.2.1.Final")
        // REMOVE when spring security has nimbus-jose-jwt >= 9.37.2
        dependency("com.nimbusds:nimbus-jose-jwt:10.3")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.named<Test>("test") {
    beforeTest(closureOf<TestDescriptor> {
        logger.lifecycle("Running test: ${this.className}.${this.name}")
    })
}

//pitest {
//    junit5PluginVersion = "1.2.1"
//    targetClasses.set(setOf("de.jkrech.tutorials.chatty.*"))
//    //mutationThreshold = 77
//    threads = 4
//    outputFormats.set(setOf("HTML"))
//    timestampedReports = false
//    failWhenNoMutations = false
//    avoidCallsTo.set(
//        setOf(
//            "kotlin.jvm.internal",
//        )
//    )
//    mutators.set(setOf("DEFAULTS"))
//    jvmArgs.set(
//        setOf(
//            "-Xms2048m",
//            "-Xmx2048m",
//            "-XX:+HeapDumpOnOutOfMemoryError",
//            "-Dfile.encoding=UTF-8"
//        )
//    )
//}

fun fullyQualifiedVersion(): String {
    return if (project.hasProperty("buildNumber")) {
        val buildNumber: String = (project.properties["buildNumber"] as String).padStart(5, '0')
        val currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))
        val projectVersion = project.properties["projectVersion"]
        "$projectVersion.$currentDate.$buildNumber"
    } else {
        "0.1-localbuild"
    }
}

tasks.register("getVersion") {
    doLast {
        println("$version")
    }
}