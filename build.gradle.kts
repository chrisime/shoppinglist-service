plugins {
    id("org.jetbrains.kotlin.jvm") version "1.4.20"
    id("org.jetbrains.kotlin.kapt") version "1.4.20"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.4.20"
    id("com.github.johnrengelman.shadow") version "6.1.0"
    id("io.micronaut.application") version "1.2.0"
}

version = "0.1"
group = "com.github.chrisime"

val kotlinVersion = project.properties["kotlinVersion"]
repositories {
    mavenCentral()
    jcenter()
}

micronaut {
    runtime("netty")
    testRuntime("kotest")
    processing {
        incremental(true)
        annotations("com.github.chrisime.*")
    }
}

dependencies {
    implementation("io.micronaut:micronaut-validation")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${kotlinVersion}")
    implementation("org.jetbrains.kotlin:kotlin-reflect:${kotlinVersion}")

    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
    implementation("io.micronaut.kotlin:micronaut-kotlin-extension-functions")
    implementation("io.micronaut:micronaut-http-client")
    implementation("io.micronaut:micronaut-runtime")
    implementation("io.micronaut.rxjava3:micronaut-rxjava3:1.1.0")

    implementation("javax.annotation:javax.annotation-api")

    runtimeOnly("ch.qos.logback:logback-classic")
    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")
}


application {
    mainClass.set("com.github.chrisime.ApplicationKt")
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

