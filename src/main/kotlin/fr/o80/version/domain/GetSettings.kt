package fr.o80.version.domain

import com.intellij.openapi.project.Project
import fr.o80.version.data.VersionStateService
import fr.o80.version.domain.model.VersionSettings

class GetSettings(
    project: Project
) {
    private val service = project.getService(VersionStateService::class.java)

    operator fun invoke(): VersionSettings {
        val state = service.read()
        return VersionSettings(
            basePath = state.basePath,
            versionCodeRegex = state.versionCodeRegex,
            versionFilePaths = state.versionFilePaths
        )
    }
}
