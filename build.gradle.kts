import java.nio.charset.StandardCharsets
import java.nio.file.Files

plugins {
    base
}

tasks.register("initializeMod") {
    group = "enderfall sdk"
    description = "Replaces the starter identity once. Use -PmodId, -PmodName, -PmodPackage, and -PmodAuthor."

    doLast {
        val marker = layout.projectDirectory.file(".enderfall-template").asFile.toPath()
        require(Files.exists(marker)) {
            "This template has already been initialized; edit settings.gradle.kts for later identity changes."
        }
        fun required(name: String): String = providers.gradleProperty(name).orNull?.trim()
            ?.takeIf(String::isNotEmpty)
            ?: error("Missing -P$name")

        val modId = required("modId")
        val modName = required("modName")
        val modPackage = required("modPackage")
        val modAuthor = required("modAuthor")
        fun kotlinString(value: String): String = value
            .replace("\\", "\\\\")
            .replace("\"", "\\\"")
            .replace("$", "\\$")
            .replace("\r", "\\r")
            .replace("\n", "\\n")
            .replace("\t", "\\t")
        require(modId.matches(Regex("[a-z][a-z0-9_]{1,63}"))) {
            "modId must contain 2-64 lowercase letters, digits, or underscores and start with a letter"
        }
        require(modPackage.matches(Regex("[a-zA-Z_$][a-zA-Z0-9_$]*(?:\\.[a-zA-Z_$][a-zA-Z0-9_$]*)+"))) {
            "modPackage must be a dotted Java package such as com.example.mymod"
        }

        val settings = layout.projectDirectory.file("settings.gradle.kts").asFile.toPath()
        var settingsText = Files.readString(settings, StandardCharsets.UTF_8)
        settingsText = settingsText
            .replace("rootProject.name = \"example-mod\"", "rootProject.name = \"${modId.replace('_', '-')}\"")
            .replace("id = \"example_mod\"", "id = \"$modId\"")
            .replace("name = \"Example Mod\"", "name = \"${kotlinString(modName)}\"")
            .replace("group = \"com.example\"", "group = \"$modPackage\"")
            .replace("entrypoint = \"com.example.ExampleMod\"", "entrypoint = \"$modPackage.ExampleMod\"")
            .replace("author = \"Your Name\"", "author = \"${kotlinString(modAuthor)}\"")
        Files.writeString(settings, settingsText, StandardCharsets.UTF_8)

        val oldSource = layout.projectDirectory.file("src/main/java/com/example/ExampleMod.java").asFile.toPath()
        val newSource = layout.projectDirectory.file(
            "src/main/java/${modPackage.replace('.', '/')}/ExampleMod.java"
        ).asFile.toPath()
        Files.createDirectories(newSource.parent)
        var sourceText = Files.readString(oldSource, StandardCharsets.UTF_8)
        sourceText = sourceText
            .replace("package com.example;", "package $modPackage;")
            .replace("example_mod", modId)
        Files.writeString(newSource, sourceText, StandardCharsets.UTF_8)
        if (oldSource != newSource) {
            Files.delete(oldSource)
        }
        Files.delete(marker)
        logger.lifecycle("Initialized $modName ($modId) in package $modPackage for $modAuthor")
    }
}
