package fr.o80.version.domain

import com.intellij.openapi.project.Project
import java.io.File

class ListVersionFiles(private val project: Project) {

    private val getSettings = GetSettings(project)

    operator fun invoke(): List<File> {
        val settings = getSettings()
        val basePath = settings.basePath
        val filePaths = settings.versionFilePaths

        return filePaths.map {
            File(project.basePath + basePath, it)
        }
    }
}
