package fr.o80.version.presentation

import com.intellij.util.ui.ListTableModel

class VersionFilesTableModel(
    private val files: MutableList<String>
) : ListTableModel<String>(
    arrayOf(StringColumnInfo("File")),
    files
) {
    override fun setValueAt(aValue: Any?, rowIndex: Int, columnIndex: Int) {
        files[rowIndex] = aValue as String
        super.setValueAt(aValue, rowIndex, columnIndex)
    }
}
