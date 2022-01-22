package fr.o80.version.action

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import fr.o80.version.presentation.VersionsDialog

class ShowVersionsScreenAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return

        VersionsDialog(
            "Bump Versions",
            project
        ).show()
    }
}
