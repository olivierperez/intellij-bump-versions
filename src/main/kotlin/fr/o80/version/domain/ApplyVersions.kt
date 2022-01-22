package fr.o80.version.domain

import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.editor.Document
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.vfs.VirtualFile
import fr.o80.version.domain.model.VersionFile

class ApplyVersions {
    operator fun invoke(project: Project, versions: List<VersionFile>) {
        WriteCommandAction.runWriteCommandAction(project) {
            versions
                .filter { it.currentVersion != it.ongoingVersion }
                .forEach { version ->
                    val document = version.virtualFile.document

                    val previousLine = version.currentVersionLine
                    val newLine = version.ongoingVersionLine

                    val start = document.text.indexOf(previousLine)
                    val end = start + previousLine.length

                    document.replaceString(start, end, newLine)
                }
        }
    }
}

private val VersionFile.virtualFile: VirtualFile
    get() = LocalFileSystem.getInstance().findFileByIoFile(this.file)!!

private val VirtualFile.document: Document
    get() = FileDocumentManager.getInstance().getDocument(this)!!

private val VersionFile.currentVersionLine: String
    get() = this.versionLine.before + this.currentVersion + (this.versionLine.after ?: "")

private val VersionFile.ongoingVersionLine: String
    get() = this.versionLine.before + this.ongoingVersion + (this.versionLine.after ?: "")
