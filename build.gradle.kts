import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application

    kotlin("jvm")
    kotlin("plugin.serialization")

    id("com.github.johnrengelman.shadow")
    id("org.web3j")
}

group = "dev.koding"
version = "1.0.0"

val main = "dev.koding.argon.AppKt"

repositories {
    // You can remove this if you're not testing locally-installed KordEx builds
    mavenLocal()

    maven("https://jitpack.io")
    maven {
        name = "Kotlin Discord"
        url = uri("https://maven.kotlindiscord.com/repository/maven-public/")
    }
}

dependencies {
    implementation(libs.kord.extensions)
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlin.reflect)
    implementation(libs.kotlinx.serialization)
    implementation(libs.kotlinx.coroutines)

    // Logging dependencies
    implementation(libs.groovy)
    implementation(libs.jansi)
    implementation(libs.logback)
    implementation(libs.logging)

    implementation(libs.prometheus.pushgateway)
    implementation(libs.ktor.client.logging)
    implementation(libs.web3j.contracts)
    implementation(libs.mojangapi)
}

// Java build

application {
    // This is deprecated, but the Shadow plugin requires it
    mainClassName = main
}

tasks.withType<KotlinCompile> {
    // Current LTS version of Java
    kotlinOptions.jvmTarget = "11"

    kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
}

tasks.jar {
    manifest {
        attributes(
            "Main-Class" to main
        )
    }
}



java {
    // Current LTS version of Java
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

// Solidity config

solidity {
    // Downloaded from: https://github.com/ethereum/solidity/releases
    val os = System.getProperty("os.name").toLowerCase()
    executable = "bin/solc/" + when {
        os.contains("win") -> "solc-windows.exe"
        os.contains("mac") -> "solc-macos"
        else -> "solc-linux"
    }

    evmVersion = org.web3j.solidity.gradle.plugin.EVMVersion.CONSTANTINOPLE
    version = "0.8.13"
}

web3j {
    generatedFilesBaseDir = "src"
}

val replaceVersions: Task by tasks.creating {
    doLast {
        ant.invokeMethod(
            "replace", mapOf(
                "file" to "build/package.json",
                "token" to "\"@openzeppelin/contracts\": \"latest\"",
                "value" to "\"@openzeppelin/contracts\": \"4.4.2\",\n\t\t\"@openzeppelin/contracts-upgradeable\": \"4.5.2\""
            )
        )
    }
}

afterEvaluate {
    replaceVersions.dependsOn(tasks.named("resolveSolidity"))
    tasks.named("npmInstall").get().dependsOn(replaceVersions)

    // God awful
    val java = tasks.compileJava.get()
    java.setDependsOn(java.dependsOn.filter { (it as? TaskProvider<*>)?.name != "generateContractWrappers" })
}