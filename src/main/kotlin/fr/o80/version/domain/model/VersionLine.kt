package fr.o80.version.domain.model

data class VersionLine(
    val before: String,
    val version: Int,
    val after: String?
)
