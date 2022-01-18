package fr.o80.version.domain

import com.intellij.openapi.project.Project
import fr.o80.version.model.VersionSettings

class GetSettings(project: Project) {
    operator fun invoke() : VersionSettings {
        return VersionSettings("TODO")
    }
}
