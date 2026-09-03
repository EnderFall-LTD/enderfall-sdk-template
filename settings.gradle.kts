pluginManagement {
    val localRepository = settingsDir.resolve("../enderfall-sdk/build/repository").canonicalFile
    repositories {
        if (localRepository.isDirectory) {
            maven { url = uri(localRepository) }
        }
        gradlePluginPortal()
        mavenCentral()
    }
}

plugins {
    id("uk.co.enderfall.sdk") version "0.1.0-beta.1"
}

dependencyResolutionManagement {
    repositories {
        val localRepository = settingsDir.resolve("../enderfall-sdk/build/repository").canonicalFile
        if (localRepository.isDirectory) {
            maven { url = uri(localRepository) }
        }
        mavenCentral()
    }
}

val localApi = settingsDir.resolve("../enderfall-sdk/api").canonicalFile
if (localApi.isDirectory) {
    includeBuild(localApi) {
        dependencySubstitution {
            substitute(module("uk.co.enderfall.sdk:enderfall-sdk-api"))
                .using(project(":"))
        }
    }
}

rootProject.name = "example-mod"

enderfallSdk {
    mod {
        id = "example_mod"
        name = "Example Mod"
        group = "com.example"
        version = "0.1.0"
        entrypoint = "com.example.ExampleMod"
        author = "Your Name"
        license = "CC0-1.0"
    }

    targets {
        version("1.21.4") { loaders("fabric", "neoforge") }
        version("26.2") { loaders("fabric", "neoforge") }
    }

    developmentTarget = "26.2-fabric"
}
