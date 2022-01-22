package fr.o80.version.data

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.RoamingType
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import fr.o80.version.data.model.VersionSettingsData
import fr.o80.version.domain.model.VersionSettings

@State(name = "VersionsSettings", storages = [Storage("bumpVersions.xml", roamingType = RoamingType.DISABLED)])
class VersionStateServiceImpl : PersistentStateComponent<VersionSettingsData>, VersionStateService {

    private var state: VersionSettingsData = VersionSettingsData()

    override fun save(versionSettings: VersionSettings) {
        with(state) {
            basePath = versionSettings.basePath
            versionFilePaths.clear()
            versionFilePaths.addAll(versionSettings.versionFilePaths)
        }
    }

    override fun read(): VersionSettings {
        return state.let { state ->
            VersionSettings(
                basePath = state.basePath,
                versionFilePaths = state.versionFilePaths
            )
        }
    }

    override fun getState(): VersionSettingsData {
        return state
    }

    override fun loadState(state: VersionSettingsData) {
        this.state = state
    }

    override fun toString(): String {
        return with(state.versionFilePaths) {
            "messageList.size=$size" + "\n${joinToString(separator = "\n")}"
        }
    }
}

interface VersionStateService {
    fun save(versionSettings: VersionSettings)
    fun read(): VersionSettings
}
