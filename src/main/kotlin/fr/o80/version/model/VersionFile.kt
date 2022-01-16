package fr.o80.version.model

import java.io.File

val versionCodeRegex = "^version_code=(\\d+)$".toRegex()

data class VersionFile(
    val filename: String,
    val file: File,
    val currentVersion: Int,
    var ongoingVersion: Int
)

fun createVersionFile(filename: String, file: File): VersionFile {
    val currentVersion = file.readVersion()
    return VersionFile(
        filename,
        file,
        currentVersion,
        currentVersion
    )
}

fun File.readVersion(): Int {
    return this
        .readLines()
        .mapNotNull { versionCodeRegex.find(it) }
        .firstOrNull()
        ?.groupValues
        ?.get(1)
        ?.toInt()
        ?: -1
}
