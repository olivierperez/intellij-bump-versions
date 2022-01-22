package fr.o80.version.presentation

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.layout.panel
import fr.o80.version.domain.ApplyVersions
import fr.o80.version.domain.ListVersionFiles
import fr.o80.version.domain.ReadVersion
import fr.o80.version.domain.model.VersionFile
import javax.swing.JComponent

class VersionsDialog(
    title: String,
    private val project: Project
) : DialogWrapper(project) {

    private val applyVersions = ApplyVersions()
    private val readVersion = ReadVersion(project)
    private val listVersionFiles = ListVersionFiles(project)

    private val versions: List<VersionFile> =
        listVersionFiles()
            .filter { it.isFile }
            .mapNotNull { file ->
                readVersion(file.name, file)
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
