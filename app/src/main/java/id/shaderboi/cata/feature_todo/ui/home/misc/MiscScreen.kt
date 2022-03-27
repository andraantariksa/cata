package id.shaderboi.cata.feature_todo.ui.home.misc

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.schnettler.datastore.compose.material3.PreferenceScreen
import de.schnettler.datastore.compose.material3.model.Preference
import id.shaderboi.cata.feature_todo.ui.AppState
import id.shaderboi.cata.feature_todo.ui.home.view_model.HomeState
import id.shaderboi.cata.ui.theme.Theme
import id.shaderboi.cata.util.ThemePref
import id.shaderboi.cata.util.dataStore

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MiscScreen(appState: AppState, homeState: HomeState) {
    val context = LocalContext.current
    val dataStore = context.dataStore

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(0.dp)
            ) {
                Box {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            "Misc",
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp,
                            modifier = Modifier.padding(start = 20.dp)
                        )
                    }
                }
            }
        },
        bottomBar = {
            BottomAppBar {}
        }
    ) {
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
                            icon = {
                                Icon(
                                    imageVector = Icons.Default.DarkMode,
                                    contentDescription = "Dark mode"
                                )
                            },
                            enabled = true,
                            entries = mapOf(
                                Theme.Light.name to "Light",
                                Theme.Dark.name to "Dark"
                            ),
                        )
                    )
                )
            ),
            dataStore = dataStore,
            modifier = Modifier
        )
    }
}