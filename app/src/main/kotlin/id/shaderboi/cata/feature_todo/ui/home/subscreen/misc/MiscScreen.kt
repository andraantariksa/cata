package id.shaderboi.cata.feature_todo.ui.home.subscreen.misc

import androidx.compose.foundation.layout.*
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import id.shaderboi.cata.BuildConfig
import id.shaderboi.cata.feature_todo.ui.common.AppState
import id.shaderboi.cata.feature_todo.ui.common.rememberAppState
import id.shaderboi.cata.feature_todo.ui.home.common.HomeState
import id.shaderboi.cata.feature_todo.ui.home.common.rememberHomeState
import id.shaderboi.cata.feature_todo.ui.home.subscreen.misc.components.TextPreferenceWidget
import id.shaderboi.cata.feature_todo.ui.home.subscreen.misc.components.ThemePreference
import id.shaderboi.cata.feature_todo.ui.home.subscreen.misc.components.preference.Preference
import id.shaderboi.cata.feature_todo.ui.home.subscreen.misc.components.preference.PreferenceGroupHeader
import id.shaderboi.cata.feature_todo.ui.home.subscreen.misc.view_model.SettingsViewModel
import id.shaderboi.cata.util.dataStore

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MiscScreen(
    appState: AppState,
    homeState: HomeState,
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val dataStore = context.dataStore

    LaunchedEffect(Unit) {
        settingsViewModel.listen(dataStore)
    }

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
        Column {
            PreferenceGroupHeader("Appearance")
            ThemePreference(appState = appState)
            PreferenceGroupHeader("About")
            TextPreferenceWidget(
                preference = remember {
                    Preference(
                        title = "Version",
                        description = BuildConfig.VERSION_NAME
                    )
                }
            )
        }
    }
}

@Preview
@Composable
private fun MiscScreenPreview() {
    MiscScreen(
        appState = rememberAppState(),
        homeState = rememberHomeState()
    )
}
