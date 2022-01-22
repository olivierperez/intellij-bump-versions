package fr.o80.version.presentation

import com.intellij.openapi.options.Configurable
import com.intellij.openapi.project.Project
import com.intellij.ui.ToolbarDecorator
import com.intellij.ui.components.JBTextField
import com.intellij.ui.layout.CellBuilder
import com.intellij.ui.layout.GrowPolicy
import com.intellij.ui.layout.panel
import com.intellij.ui.table.TableView
import fr.o80.version.domain.GetSettings
import fr.o80.version.domain.SaveSettings
import javax.swing.JComponent

class VersionsConfigurable(
    private val project: Project
) : Configurable {

    private val getSettings = GetSettings(project)
    private val saveSettings = SaveSettings(project)

    private var initialSettings = getSettings()
    private val settings = initialSettings.cloned()

    private lateinit var basePathField: CellBuilder<JBTextField>
    private lateinit var codeRegexField: CellBuilder<JBTextField>
    private val tableModel = VersionFilesTableModel(settings.versionFilePaths)

    override fun createComponent(): JComponent {
        return panel {
            row {
                label("Base path (${project.basePath}/...)")
                basePathField = textField(
                    getter = { settings.basePath },
                    setter = { settings.basePath = it },
                ).focused()
            }

            row {
                label("Version code regex")
                codeRegexField = textField(
                    getter = { settings.versionCodeRegex },
                    setter = { settings.versionCodeRegex = it },
                )
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
        settings.basePath = basePathField.component.text
        settings.versionCodeRegex = codeRegexField.component.text

        return initialSettings != settings
    }

    override fun apply() {
        settings.basePath = basePathField.component.text
        settings.versionCodeRegex = codeRegexField.component.text

        saveSettings(settings)
        initialSettings = settings.cloned()
    }

    override fun getDisplayName(): String {
        return "Bump Versions Settings"
    }
}
