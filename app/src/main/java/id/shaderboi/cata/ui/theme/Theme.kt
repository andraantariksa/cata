package id.shaderboi.cata.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun CataAppTheme(theme: Theme, content: @Composable () -> Unit) {
    val colors = when (theme) {
        Theme.Dark -> darkColors(primary = Color(0xff99c3c1))
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