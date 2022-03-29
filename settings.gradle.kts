pluginManagement {
    plugins {
        // Update this in libs.version.toml when you change it here
        kotlin("jvm") version "1.6.10"
        kotlin("plugin.serialization") version "1.6.10"

        id("com.github.johnrengelman.shadow") version "5.2.0"
        id("org.web3j") version "4.9.0"
    }
}

rootProject.name = "argon"

enableFeaturePreview("VERSION_CATALOGS")

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("libs.versions.toml"))
        }
    }
}
