package fr.o80.version.presentation

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.layout.panel
import fr.o80.version.domain.ApplyVersions
import fr.o80.version.domain.ReadVersion
import fr.o80.version.domain.model.VersionFile
import java.io.File
import javax.swing.JComponent

class VersionsDialog(
    title: String,
    private val project: Project,
    basePath: String,
    versionFiles: List<String>
) : DialogWrapper(project) {

    private val applyVersions = ApplyVersions()
    private val readVersion = ReadVersion()

    private val versions: List<VersionFile> =
        versionFiles
            .filter { filename -> File(basePath, filename).isFile }
            .mapNotNull { filename ->
                val file = File(basePath, filename)
                readVersion(filename, file)
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
        applyVersions(project, versions)
    }
}
