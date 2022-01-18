package fr.o80.version.presentation

import com.intellij.openapi.options.Configurable
import com.intellij.openapi.project.Project
import com.intellij.ui.ToolbarDecorator
import com.intellij.ui.layout.GrowPolicy
import com.intellij.ui.layout.panel
import com.intellij.ui.table.TableView
import fr.o80.version.domain.GetSettings
import fr.o80.version.domain.SaveSettings
import fr.o80.version.model.VersionSettings
import javax.swing.JComponent

class VersionsConfigurable(
    private val project: Project
) : Configurable {

    private val getSettings = GetSettings(project)
    private val saveSettings = SaveSettings(project)

    private val initialSettings = getSettings()
    // TODO OPZ C'est pas beau cette copie de VersionSettings
    private val settings = VersionSettings(initialSettings.basePath, ArrayList(initialSettings.versionFilePaths))

    private val tableModel = VersionFilesTableModel(settings.versionFilePaths)

    override fun createComponent(): JComponent {
        return panel {
            row {
                label("Base path (${project.basePath}/...)")
                textField(
                    getter = { settings.basePath },
                    setter = { settings.basePath = it }
                ).focused()
            }

            row {
                component(
                    ToolbarDecorator
                        .createDecorator(
                            TableView(
                                tableModel
                            ).apply {
                                isStriped = true
                            }
                        )
                        .setAddAction {
                            tableModel.addRow("not_yet_defined")
                            tableModel.fireTableDataChanged()
                        }
                        .createPanel()
                ).growPolicy(GrowPolicy.MEDIUM_TEXT)
            }
        }
    }

    override fun isModified(): Boolean {
        return initialSettings != settings
    }

    override fun apply() {
        saveSettings(settings)
    }

    override fun getDisplayName(): String {
        return "Bump Versions Settings"
    }
}
