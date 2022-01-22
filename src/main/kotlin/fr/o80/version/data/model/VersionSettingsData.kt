package fr.o80.version.data.model

import java.util.concurrent.CopyOnWriteArrayList

data class VersionSettingsData(
    var basePath: String = "/",
    var versionCodeRegex: String = "^(version_code=)(\\d+)$",
    var versionFilePaths: MutableList<String> = CopyOnWriteArrayList()
)
