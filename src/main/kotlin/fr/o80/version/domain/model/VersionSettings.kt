package fr.o80.version.domain.model

import java.util.concurrent.CopyOnWriteArrayList

data class VersionSettings(
    var basePath: String,
    var versionCodeRegex: String,
    var versionFilePaths: MutableList<String>
) {
    fun cloned(): VersionSettings {
        return VersionSettings(basePath, versionCodeRegex, CopyOnWriteArrayList(versionFilePaths))
    }
}
