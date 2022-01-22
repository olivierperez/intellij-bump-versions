package fr.o80.version.domain.model

import java.io.File

data class VersionFile(
    val filename: String,
    val file: File,
    val versionLine: VersionLine,
    val currentVersion: Int,
    var ongoingVersion: Int
)
