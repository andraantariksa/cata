package id.shaderboi.cata.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableStateOf
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import id.shaderboi.cata.feature_todo.ui.AppScreen
import id.shaderboi.cata.ui.theme.CataAppTheme
import id.shaderboi.cata.ui.theme.Theme
import id.shaderboi.cata.util.ThemePref
import id.shaderboi.cata.util.dataStore
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private var theme = mutableStateOf<Theme>(Theme.Light)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        listenThemeChange()

        setContent {
            CataAppTheme(theme.value) {
                AppScreen()
            }
        }
    }

    private fun listenThemeChange() {
        dataStore.data.onEach { pref ->
            theme.value = when (pref[ThemePref.key]) {
                Theme.Dark.name -> Theme.Dark
                // Default to light
                else -> Theme.Light
            }
        }.launchIn(lifecycleScope)
    }
}