import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm") version "1.4.21"

    idea

    id("org.jetbrains.kotlin.kapt") version "1.4.21"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.4.21"

    id("com.github.johnrengelman.shadow") version "6.1.0"
    id("io.micronaut.application") version "1.2.0"

    id("nu.studer.jooq") version "5.2"
    id("org.flywaydb.flyway") version "7.3.1"
}

version = "0.1"
group = "com.github.chrisime"

repositories {
    mavenLocal()
    mavenCentral()
    jcenter()
    maven {
        setUrl("https://dl.bintray.com/chrisime/oss/")
    }
}

idea {
    module {
        isDownloadSources = true
        isDownloadJavadoc = true
    }
    project {
    }
}

micronaut {
    version("${properties["micronautVersion"]}")

    runtime("netty")
    testRuntime("kotest")
    processing {
        incremental(true)
        module(project.name)
        group("${project.group}")
        annotations("com.github.chrisime.*")
    }
}

dependencies {
    kapt(platform("io.micronaut:micronaut-bom:${properties["micronautVersion"]}"))
    kapt("io.micronaut:micronaut-inject-java")
    kapt("io.micronaut:micronaut-validation")

    implementation(enforcedPlatform("org.jetbrains.kotlin:kotlin-bom:${properties["kotlinVersion"]}"))

    implementation(platform("io.micronaut:micronaut-bom:${properties["micronautVersion"]}"))

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

    implementation("xyz.chrisime:crood:${properties["croodVersion"]}")

    runtimeOnly("io.micronaut.sql:micronaut-jdbc-hikari")
    runtimeOnly("org.postgresql:postgresql")

    runtimeOnly("ch.qos.logback:logback-classic")
    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")

    jooqGenerator("org.postgresql:postgresql:42.2.18")
    jooqGenerator("xyz.chrisime:crood:${properties["croodVersion"]}")

}

configurations.all {
    resolutionStrategy.dependencySubstitution {
        substitute(module("org.jooq:jooq-kotlin"))
            .using(module("org.jooq:jooq-kotlin:${properties["jooqVersion"]}"))
            .withoutClassifier()

        substitute(module("org.jooq:jooq"))
            .using(module("org.jooq:jooq:${properties["jooqVersion"]}"))
            .withoutClassifier()

        substitute(module("org.jooq:jooq-codegen"))
            .using(module("org.jooq:jooq-codegen:${properties["jooqVersion"]}"))
            .withoutClassifier()

        substitute(module("org.jooq:jooq-meta"))
            .using(module("org.jooq:jooq-meta:${properties["jooqVersion"]}"))
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

flyway {
    url = "jdbc:postgresql://localhost/shopping_list"
    user = "user"
    password = "password"
    schemas = arrayOf("public")
    locations = arrayOf("filesystem:src/main/resources/db/migration")
}

jooq {
    version.set("${properties["jooqVersion"]}")
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
                    name = "xyz.chrisime.crood.codegen.KDomainGenerator"
                    strategy.name = "xyz.chrisime.crood.codegen.DomainGeneratorStrategy"
                    database.apply {
                        name = "org.jooq.meta.postgres.PostgresDatabase"
                        inputSchema = "public"
                        recordVersionFields = "version"
                    }
                    generate.apply {
                        isNullableAnnotation = true
                        isNonnullAnnotation = true
                        isValidationAnnotations = true
                        isDeprecated = false
                        isRecords = true
                        isImmutablePojos = true
                        isRoutines = true
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

val generateJooq by project.tasks
generateJooq.dependsOn("flywayMigrate")

val build by project.tasks
build.dependsOn(generateJooq)
