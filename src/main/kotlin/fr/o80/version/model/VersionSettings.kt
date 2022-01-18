package fr.o80.version.model

data class VersionSettings(
    var basePath: String = "/",
    val versionFilePaths: MutableList<String> = mutableListOf("A", "B", "C")
)
