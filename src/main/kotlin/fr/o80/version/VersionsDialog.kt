package fr.o80.version

import com.intellij.openapi.application.runWriteAction
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.layout.panel
import fr.o80.version.model.VersionFile
import fr.o80.version.model.createVersionFile
import fr.o80.version.model.versionCodeRegex
import java.io.File
import javax.swing.JComponent

class VersionsDialog(
    title: String,
    project: Project?,
    basePath: String,
    versionFiles: List<String>
) : DialogWrapper(project) {

    private val versions: List<VersionFile> =
        versionFiles
            .filter { filename -> File(basePath, filename).isFile }
            .map { filename ->
                val file = File(basePath, filename)
                createVersionFile(filename, file)
            }

    init {
        setTitle(title)
        setOKButtonText("Apply")
        init()
    }

    override fun createCenterPanel(): JComponent {
        return panel {
            row {
                label("")
                label("Current version")
                label("New version")
            }
            versions
                .forEach { versionFile ->
                    row {
                        label(versionFile.filename)
                        label(versionFile.currentVersion.toString())
                        intTextField(
                            getter = { versionFile.currentVersion },
                            setter = { versionFile.ongoingVersion = it }
                        )
                    }
                }
        }
    }

    override fun doOKAction() {
        super.doOKAction()
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
