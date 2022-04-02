package id.shaderboi.cata.feature_todo.ui.home.subscreen.misc.components

import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.datastore.preferences.core.edit
import id.shaderboi.cata.feature_todo.ui.common.AppState
import id.shaderboi.cata.feature_todo.ui.common.components.SelectDialog
import id.shaderboi.cata.feature_todo.ui.home.subscreen.misc.components.preference.Preference
import id.shaderboi.cata.feature_todo.ui.home.subscreen.misc.components.preference.iconModifier
import id.shaderboi.cata.ui.theme.Theme
import id.shaderboi.cata.util.dataStore
import id.shaderboi.cata.util.themePrefKey
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun ThemePreference(appState: AppState) {
    val context = LocalContext.current
    val dataStore = context.dataStore

    var isDialogShown by remember { mutableStateOf(false) }
    var selectedTheme by remember { mutableStateOf(0) }
    val themeSelection =
        remember { Theme.values().map { theme -> theme.toString() } }.toTypedArray()

    val preferenceState = remember {
        Preference(
            title = "Dark mode",
            description = "For those of you who can't stand in a daylight",
            icon = {
                Icon(
                    imageVector = Icons.Default.DarkMode,
                    contentDescription = "Dark mode",
                    modifier = iconModifier
                )
            },
            enabled = true,
        )
    }

    LaunchedEffect(Unit) {
        dataStore.data.collectLatest { preferences ->
            selectedTheme = preferences[themePrefKey] ?: 0
        }
    }

    TextPreferenceWidget(
        preference = preferenceState,
        onClick = { isDialogShown = !isDialogShown }
    )

    if (isDialogShown) {
        SelectDialog(
            selectedIndex = selectedTheme,
            options = themeSelection,
            onClick = { idx, _ ->
                appState.appScope.launch {
                    dataStore.edit { preferences ->
                        preferences.apply {
                            this[themePrefKey] = idx
                        }
                    }
                }

                isDialogShown = false
            },
            onDismissRequest = {
                isDialogShown = false
            },
            title = {
                Text("Select theme", fontWeight = FontWeight.Bold)
            }
        )
    }
}