package id.shaderboi.cata.feature_todo.ui.home.misc

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.preferences.core.stringPreferencesKey
import de.schnettler.datastore.compose.material3.PreferenceScreen
import de.schnettler.datastore.compose.material3.model.Preference
import de.schnettler.datastore.compose.material3.widget.PreferenceIcon
import de.schnettler.datastore.manager.DataStoreManager
import de.schnettler.datastore.manager.PreferenceRequest
import id.shaderboi.cata.R
import id.shaderboi.cata.feature_todo.ui.AppState
import id.shaderboi.cata.feature_todo.ui.home.HomeState
import id.shaderboi.cata.ui.theme.Theme
import id.shaderboi.cata.util.ThemePref
import id.shaderboi.cata.util.dataStore
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MiscScreen(appState: AppState, homeState: HomeState) {
    val context = LocalContext.current
    val dataStore = context.dataStore

    PreferenceScreen(
        items = listOf(
            Preference.PreferenceGroup(
                title = "Appearance",
                enabled = true,
                preferenceItems = listOf(
                    Preference.PreferenceItem.ListPreference(
                        request = ThemePref,
                        title = "Theme",
                        summary = "For those of you who can't stand in a daylight",
                        singleLineTitle = true,
                        icon = { PreferenceIcon(icon = Icons.Default.DarkMode) },
                        enabled = true,
                        entries = mapOf(
                            Theme.Light.name to "Light",
                            Theme.Dark.name to "Dark"
                        )
                    )
                )
            )
        ),
        dataStore = dataStore
    )
}