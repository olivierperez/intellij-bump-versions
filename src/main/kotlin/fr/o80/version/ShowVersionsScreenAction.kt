package fr.o80.version

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class ShowVersionsScreenAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        val basePath = project.basePath!!

        VersionsDialog(
            "Bump Versions",
            project,
            "$basePath/config",
            listOf(
                "aqueuse/mobile.properties",
                "aqueuse/tv.properties",
                "skarab42/mobile.properties",
                "skarab42/tv.properties"
            )
        ).show()
    }
}
