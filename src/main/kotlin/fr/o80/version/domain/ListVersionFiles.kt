package fr.o80.version.domain

import com.intellij.openapi.project.Project
import java.io.File

class ListVersionFiles(private val project: Project) {

    private val getSettings = GetSettings(project)

    operator fun invoke(): List<Pair<String, File>> {
        val settings = getSettings()
        val basePath = settings.basePath
        val filePaths = settings.versionFilePaths

        return filePaths.map { filename ->
            Pair(filename, File(project.basePath + basePath, filename))
        }
    }
}
