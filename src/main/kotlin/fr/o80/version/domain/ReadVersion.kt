package fr.o80.version.domain

import com.intellij.openapi.project.Project
import fr.o80.version.domain.model.VersionFile
import fr.o80.version.domain.model.VersionLine
import java.io.File

class ReadVersion(project: Project) {

    private val getSettings = GetSettings(project)

    operator fun invoke(filename: String, file: File): VersionFile? {
        val versionLine = file.readVersion() ?: return null
        return VersionFile(
            filename,
            file,
            versionLine,
            versionLine.version,
            versionLine.version
        )
    }

    private fun File.readVersion(): VersionLine? {
        val versionCodeRegex = getSettings().versionCodeRegex.toRegex()
        val groupValues = this
            .readLines()
            .mapNotNull { versionCodeRegex.find(it) }
            .firstOrNull()
            ?.groupValues ?: return null
        return VersionLine(
            groupValues[1],
            groupValues[2].toInt(),
            groupValues.getOrNull(3)
        )
    }
}
