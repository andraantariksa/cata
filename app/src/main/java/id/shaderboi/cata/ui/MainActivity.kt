package id.shaderboi.cata.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableStateOf
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.google.accompanist.insets.ProvideWindowInsets
import dagger.hilt.android.AndroidEntryPoint
import id.shaderboi.cata.feature_todo.ui.AppScreen
import id.shaderboi.cata.ui.theme.CataAppTheme
import id.shaderboi.cata.ui.theme.Theme
import id.shaderboi.cata.util.ThemePref
import id.shaderboi.cata.util.dataStore
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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
        lifecycleScope.launch {
            dataStore.data.collectLatest { pref ->
                theme.value = when (pref[ThemePref.key]) {
                    Theme.Dark.name ->  Theme.Dark
                    // Default to light
                    else -> Theme.Light
                }
            }
        }
    }
}