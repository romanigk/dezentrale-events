import io.ktor.plugin.features.*
import org.openapitools.generator.gradle.plugin.tasks.GenerateTask

val ktorVersion: String by project
val kotlinVersion: String by project
val logbackVersion: String by project
val postgresVersion: String by project
val h2Version: String by project
val hikaricpVersion: String by project
val ehcacheVersion: String by project
val exposedVersion: String by project
val dropwizardMetricsCore: String by project

plugins {
    kotlin("jvm") version "2.1.20"
    id("io.ktor.plugin") version "3.1.1"
    id("org.jetbrains.kotlin.plugin.serialization") version "2.1.10"
    id("org.openapi.generator") version "7.12.0"
}

group = "space.dezentrale.prgrnd"
version = "0.0.1"
application {
    mainClass.set("space.dezentrale.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktorVersion")
    implementation("org.postgresql:postgresql:$postgresVersion")
    implementation("com.h2database:h2:$h2Version")
    implementation("io.ktor:ktor-server-auth:$ktorVersion")
    implementation("io.ktor:ktor-server-auto-head-response:$ktorVersion")
    implementation("io.ktor:ktor-server-default-headers:$ktorVersion")
    implementation("io.ktor:ktor-server-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-serialization-gson:$ktorVersion")
    implementation("io.ktor:ktor-server-resources:$ktorVersion")
    implementation("io.ktor:ktor-server-hsts:$ktorVersion")
    implementation("io.ktor:ktor-server-compression:$ktorVersion")
    implementation("io.ktor:ktor-server-metrics:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("io.dropwizard.metrics:metrics-core:$dropwizardMetricsCore")
    implementation("com.zaxxer:HikariCP:$hikaricpVersion")
    implementation("org.ehcache:ehcache:$ehcacheVersion")
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktorVersion")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")
}

ktor {
    docker {
        jreVersion.set(JavaVersion.VERSION_21)
        externalRegistry.set(
            DockerImageRegistry.dockerHub(
                appName = provider { "dezentrale-events" },
                username = providers.environmentVariable("DOCKER_HUB_USERNAME"),
                password = providers.environmentVariable("DOCKER_HUB_PASSWORD")
            )
        )
    }
}

val openApiValidate = openApiValidate {
    inputSpec.set("$rootDir/src/main/resources/META-INF/openapi/dezentrale-events.yml")
    recommend.set(true)
}

val openApiGeneratedSourcesFolder = "${layout.buildDirectory.get()}/generated-sources"
val deleteSuperfluousGeneratedFiles = tasks.register<Delete>("deleteSuperfluousGeneratedFiles") {
    group = "space.dezentrale.prgrnd"
    description = "This task deletes generated source code that is not needed."
    delete(
        file("${openApiGeneratedSourcesFolder}/.openapi-generator-ignore"),
        file("${openApiGeneratedSourcesFolder}/build.gradle"),
        file("${openApiGeneratedSourcesFolder}/pom.xml"),
        file("${openApiGeneratedSourcesFolder}/src/main/resources"),
        file("${openApiGeneratedSourcesFolder}/gradle.properties"),
        file("${openApiGeneratedSourcesFolder}/README.md"),
        file("${openApiGeneratedSourcesFolder}/docker-compose.yml"),
        file("${openApiGeneratedSourcesFolder}/Dockerfile"),
        file("${openApiGeneratedSourcesFolder}/settings.gradle"),
        file("${openApiGeneratedSourcesFolder}/.openapi-generator-ignore"),
    )
}

val generateDezentraleEventsFromOpenApi =
    tasks.register<GenerateTask>("generateDezentraleEventsFromOpenApi") {
        group = "space.dezentrale.prgrnd"
        description = """This task generates kotlin (ktor) code from an Open API Spec, which you 
                can find under META_INF/openapi/dezentrale-events.yml."""
        groupId.set("space.dezentrale.prgrnd")
        generatorName.set("kotlin-server")
        inputSpec.set("$rootDir/src/main/resources/META-INF/openapi/dezentrale-events.yml")
        apiPackage.set("space.dezentrale.events.api")
        modelPackage.set("space.dezentrale.events.model")
        outputDir.set(openApiGeneratedSourcesFolder)

        configOptions.set(
            mapOf(
                "groupId" to "space.dezentrale.prgrnd",
                "artifactId" to "dezentrale-events",
                "artifactVersion" to "0.0.1",
                "interfaceOnly" to "true",
                "enumPropertyNaming" to "original",
                "packageName" to "space.dezentrale.events"
            )
        )
        finalizedBy(deleteSuperfluousGeneratedFiles)
    }

kotlin {
    sourceSets {
        main {
            listOf(
                "src/main/kotlin",
                "${openApiGeneratedSourcesFolder}/src/main/kotlin"
            )
        }
    }
}

tasks.named("compileKotlin") {
    dependsOn(generateDezentraleEventsFromOpenApi)
}
