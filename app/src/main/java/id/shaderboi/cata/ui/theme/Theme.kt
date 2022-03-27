package id.shaderboi.cata.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import id.shaderboi.cata.feature_todo.ui.util.LocalTheme

@Composable
fun CataAppTheme(theme: Theme, content: @Composable () -> Unit) {
    val colors = when (theme) {
        Theme.Dark -> darkColors(
            primary = Color(0xFF263238),
            secondary = Color.White,
            onPrimary = Color.White,
        )
        Theme.Light -> lightColors(
            primary = Color(0xFF26A69A)
        )
    }

    CompositionLocalProvider(LocalTheme provides theme) {
        MaterialTheme(
            colors = colors,
            content = content
        )
    }
}

sealed class Theme(val name: String) {
    object Dark : Theme("dark")
    object Light : Theme("light")
}