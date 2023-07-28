plugins {
    id("org.jetbrains.kotlin.jvm") version "1.6.21"
    id("org.jetbrains.kotlin.kapt") version "1.6.21"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.6.21"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("io.micronaut.application") version "3.7.9"
    id("org.flywaydb.flyway") version "8.2.2"
    id("nu.studer.jooq") version "7.1.1"

}

buildscript{
    repositories{
        mavenCentral()
    }
    dependencies {
        classpath("org.flywaydb:flyway-mysql:8.3.0")
    }
}

version = "0.1"
group = "com.thanesh.udemy.micronaut.tutorial"

val kotlinVersion = project.properties.get("kotlinVersion")
repositories {
    mavenCentral()
}

dependencies {
    kapt("io.micronaut:micronaut-http-validation")
    annotationProcessor("io.micronaut.data:micronaut-data-processor")
    implementation("io.micronaut:micronaut-http-client")
    implementation("io.micronaut:micronaut-jackson-databind")
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
    implementation("jakarta.annotation:jakarta.annotation-api")
    implementation("org.jetbrains.kotlin:kotlin-reflect:${kotlinVersion}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${kotlinVersion}")
    runtimeOnly("ch.qos.logback:logback-classic")
    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")

    implementation("org.flywaydb:flyway-mysql")
    implementation("io.micronaut.flyway:micronaut-flyway")
    runtimeOnly("com.mysql:mysql-connector-j:8.0.32")

    implementation("io.micronaut.sql:micronaut-jdbc-hikari")
    implementation("io.micronaut.data:micronaut-data-jdbc")

    implementation("io.micronaut.sql:micronaut-jooq")
    jooqGenerator("com.mysql:mysql-connector-j:8.0.32")
}

val databaseUsername = "root"
val databasePassword = "password"
val databaseSchema = "learn_micronaut"
val databaseUrl = "jdbc:mysql://localhost:52000/$databaseSchema"
val databaseDriver = "com.mysql.cj.jdbc.Driver"

flyway {
    url = databaseUrl
    user = databaseUsername
    password = databasePassword
    schemas = arrayOf(databaseSchema)
}

val generatedDir = "src/main/generated"
jooq{
    configurations {
        create("main"){
            jooqConfiguration.apply {
                jdbc.apply {
                    driver = databaseDriver
                    url = databaseUrl
                    username = databaseUsername
                    password = databasePassword
                }
                generator.apply {
                    database.apply {
                        inputSchema = databaseSchema
                    }
                    generate.apply {
                        isPojosAsKotlinDataClasses = true
                        isDeprecated = true
                        isRecords = true
                        isImmutablePojos = true
                        isFluentSetters = true
                    }
                    target.apply {
                        packageName = "com.thanesh.udemy.micronaut.tutorial.db"
                        directory = "${project.projectDir}/$generatedDir"
                    }
                }
            }
        }
    }
}

tasks.clean {
    doLast{
        project.file(generatedDir).deleteRecursively()
    }
}

tasks.named("generateJooq"){
    dependsOn("flywayMigrate")
}

tasks.compileKotlin {
    dependsOn("generateJooq")
}

application {
    mainClass.set("com.thanesh.udemy.micronaut.tutorial.ApplicationKt")
}
java {
    sourceCompatibility = JavaVersion.toVersion("11")
}

tasks {
    compileKotlin {
        kotlinOptions {
            jvmTarget = "11"
        }
    }
    compileTestKotlin {
        kotlinOptions {
            jvmTarget = "11"
        }
    }
}
graalvmNative.toolchainDetection.set(false)
micronaut {
    runtime("netty")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("com.thanesh.udemy.micronaut.tutorial.*")
    }
}



