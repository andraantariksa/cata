package id.shaderboi.cata.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import id.shaderboi.cata.util.ThemePref
import id.shaderboi.cata.util.dataStore
import kotlinx.coroutines.flow.collectLatest

@Composable
fun CataAppTheme(theme: Theme, content: @Composable () -> Unit) {
    val colors = when (theme) {
        Theme.Dark -> darkColors()
        Theme.Light -> lightColors()
    }

    MaterialTheme(
        colors = colors,
        content = content
    )
}

sealed class Theme(val name: String) {
    object Dark : Theme("dark")
    object Light : Theme("light")
}