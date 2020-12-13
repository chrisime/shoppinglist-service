import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm") version "1.4.21"

    id("org.jetbrains.kotlin.kapt") version "1.4.21"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.4.21"

    id("com.github.johnrengelman.shadow") version "6.1.0"
    id("io.micronaut.application") version "1.2.0"

    id("nu.studer.jooq") version "5.2"
}

version = "0.1"
group = "com.github.chrisime"

val kotlinVersion = "1.4.21"

repositories {
    mavenLocal()
    mavenCentral()
    jcenter()
    maven {
        setUrl("https://dl.bintray.com/chrisime/oss/")
    }
}

micronaut {
    version("2.2.1")

    runtime("netty")
    testRuntime("kotest")
    processing {
        incremental(false)
        module(project.name)
        group("${project.group}")
        annotations("com.github.chrisime.*")
    }
}

dependencies {
    kapt(platform("io.micronaut:micronaut-bom:2.2.1"))
    kapt("io.micronaut:micronaut-inject-java")
    kapt("io.micronaut:micronaut-validation")

    implementation(enforcedPlatform("org.jetbrains.kotlin:kotlin-bom:${kotlinVersion}"))

    implementation(platform("io.micronaut:micronaut-bom:2.2.1"))

    implementation("io.micronaut.cache:micronaut-cache-caffeine")
    implementation("io.micronaut.flyway:micronaut-flyway")
    implementation("io.micronaut:micronaut-http-client")
    implementation("io.micronaut:micronaut-inject-java")
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
    implementation("io.micronaut.kotlin:micronaut-kotlin-extension-functions")
    implementation("io.micronaut:micronaut-management")
    implementation("io.micronaut.rxjava3:micronaut-rxjava3")
    implementation("io.micronaut.sql:micronaut-jooq")
    implementation("io.micronaut:micronaut-runtime")
    implementation("io.micronaut:micronaut-validation")

    implementation("xyz.chrisime:crood:0.1.0-SNAPSHOT")

    runtimeOnly("io.micronaut.sql:micronaut-jdbc-hikari")
    runtimeOnly("org.postgresql:postgresql")

    runtimeOnly("ch.qos.logback:logback-classic")
    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")

    jooqGenerator("org.postgresql:postgresql:42.2.18")
    jooqGenerator("xyz.chrisime:crood:0.1.0-SNAPSHOT")

}

configurations.all {
    resolutionStrategy.dependencySubstitution {
        substitute(module("org.jooq:jooq-kotlin"))
            .using(module("org.jooq:jooq-kotlin:3.14.4"))
            .withoutClassifier()

        substitute(module("org.jooq:jooq"))
            .using(module("org.jooq:jooq:3.14.4"))
            .withoutClassifier()

        substitute(module("org.jooq:jooq-codegen"))
            .using(module("org.jooq:jooq-codegen:3.14.4"))
            .withoutClassifier()

        substitute(module("org.jooq:jooq-meta"))
            .using(module("org.jooq:jooq-meta:3.14.4"))
            .withoutClassifier()
    }
}

application {
    mainClass.set("com.github.chrisime.ApplicationKt")
}

tasks.withType<ShadowJar> {
    minimize()
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
}

tasks {
    compileKotlin {
        kotlinOptions {
            jvmTarget = "11"
            freeCompilerArgs = listOf(
                "-Xjsr305=strict",
                "-Xstrict-java-nullability-assertions",
                "-Xuse-experimental=kotlin.Experimental"
            )
            apiVersion = "1.4"
            languageVersion = "1.4"
        }
    }
    compileTestKotlin {
        kotlinOptions {
            jvmTarget = "11"
            freeCompilerArgs = listOf(
                "-Xjsr305=strict",
                "-Xstrict-java-nullability-assertions",
                "-Xuse-experimental=kotlin.Experimental"
            )
            apiVersion = "1.4"
            languageVersion = "1.4"
        }
    }


}

jooq {
    version.set("3.14.4")
    edition.set(nu.studer.gradle.jooq.JooqEdition.OSS)

    configurations {
        create("main") {
            jooqConfiguration.apply {
                generateSchemaSourceOnCompilation.set(false)
                logging = org.jooq.meta.jaxb.Logging.INFO
                jdbc.apply {
                    driver = "org.postgresql.Driver"
                    url = "jdbc:postgresql://localhost/shopping_list"
                    user = "user"
                    password = "password"
                }
                generator.apply {
                    name = "xyz.chrisime.crood.codegen.DomainGenerator"
                    strategy.name = "xyz.chrisime.crood.codegen.DomainGeneratorStrategy"
                    database.apply {
                        name = "org.jooq.meta.postgres.PostgresDatabase"
                        inputSchema = "public"
                        recordVersionFields = "version"
                    }
                    generate.apply {
                        isNullableAnnotation = true
//                        isNonnullAnnotation = true
                        isValidationAnnotations = true
                        isDeprecated = false
                        isRecords = true
                        isImmutablePojos = true
                    }
                    target.apply {
                        packageName = "com.github.chrisime"
                        directory = "${project.buildDir}/generated/jooq"
                    }
                }
            }
        }
    }
}
