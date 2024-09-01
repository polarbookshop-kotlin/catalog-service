import org.springframework.boot.gradle.tasks.bundling.BootBuildImage
import org.springframework.boot.gradle.tasks.run.BootRun

plugins {
    id("org.springframework.boot") version "3.3.2"
    id("io.spring.dependency-management") version "1.1.6"
    kotlin("jvm") version "1.9.24"
    kotlin("plugin.spring") version "1.9.24"
}
val springCloudVersion by extra("2023.0.3")

group = "com.polarbookshop"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.cloud:spring-cloud-starter-config")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-database-postgresql")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.testcontainers:postgresql")
    runtimeOnly("org.postgresql:postgresql")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    // https://mvnrepository.com/artifact/io.github.microutils/kotlin-logging-jvm
    implementation("io.github.oshai:kotlin-logging-jvm:5.1.4")
    // https://mvnrepository.com/artifact/org.springframework.retry/spring-retry
    implementation("org.springframework.retry:spring-retry:2.0.6")

}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}


configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

tasks.named<BootRun>("bootRun") {
    systemProperty("spring.profiles.active", "testdata")
}
dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:$springCloudVersion")
    }
}

tasks.named<BootBuildImage>("bootBuildImage"){
    builder = "docker.io/paketobuildpacks/builder-jammy-base"
    imageName = project.name
    environment = mapOf("BP_JVM_VERSION" to "17")
    docker {
        publishRegistry {
            username = project.findProperty("registryUsername") as String?
            password= project.findProperty("registryToken") as String?
            url=project.findProperty("registryUrl") as String?
        }
    }
}
