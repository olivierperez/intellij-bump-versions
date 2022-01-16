package fr.o80.version.domain

import com.intellij.openapi.application.runWriteAction
import fr.o80.version.model.VersionFile

class ApplyVersions {
    operator fun invoke(versions: List<VersionFile>) {
        runWriteAction {
            versions
                .filter { it.currentVersion != it.ongoingVersion }
                .forEach { version ->
                    val newContent = version.file
                        .readLines()
                        .joinToString("\n") { line ->
                            val match = versionCodeRegex.find(line)
                            if (match == null) {
                                line
                            } else {
                                line.replace(version.currentVersion.toString(), version.ongoingVersion.toString())
                            }
                        }
                    version.file.writeText(newContent)
                }
        }
    }
}
