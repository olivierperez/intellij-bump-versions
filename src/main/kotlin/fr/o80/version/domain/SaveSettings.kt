package fr.o80.version.domain

import com.intellij.openapi.project.Project
import fr.o80.version.data.VersionStateService
import fr.o80.version.domain.model.VersionSettings

class SaveSettings(
    project: Project
) {
    private val service = project.getService(VersionStateService::class.java)

    operator fun invoke(settings: VersionSettings) {
        service.save(settings)
    }
}
