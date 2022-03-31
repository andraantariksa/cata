package id.shaderboi.cata.feature_todo.ui.home.subscreen.misc.view_model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.shaderboi.cata.ui.theme.Theme
import id.shaderboi.cata.util.themePrefKey
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor() : ViewModel() {
    private var _settings by mutableStateOf(SettingsState())
    val settings get() = _settings

    suspend fun listen(dataStore: DataStore<Preferences>) {
        dataStore.data.collectLatest { preferences ->
            _settings = settings.copy(
                theme = Theme.values()[preferences[themePrefKey] ?: 0]
            )
        }
    }
}