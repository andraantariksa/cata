package id.shaderboi.cata.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import id.shaderboi.cata.feature_todo.ui.AppScreen
import id.shaderboi.cata.ui.theme.CataAppTheme
import id.shaderboi.cata.ui.theme.Theme
import id.shaderboi.cata.util.dataStore
import id.shaderboi.cata.util.themePrefKey
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private var theme by mutableStateOf<Theme>(Theme.Light)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        listenThemeChange()

        setContent {
            CataAppTheme(theme) {
                AppScreen()
            }
        }
    }

    private fun listenThemeChange() {
        dataStore.data.onEach { preferences ->
            theme = Theme.values()[preferences[themePrefKey] ?: 0]
        }.launchIn(lifecycleScope)
    }
}