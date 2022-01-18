package fr.o80.version.presentation

import com.intellij.util.ui.ColumnInfo

class StringColumnInfo(name: String) : ColumnInfo<String, String>(name) {
    override fun valueOf(item: String?): String? = item
    override fun isCellEditable(item: String?): Boolean = true
}
