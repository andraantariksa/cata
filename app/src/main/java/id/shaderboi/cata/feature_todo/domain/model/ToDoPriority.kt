package id.shaderboi.cata.feature_todo.domain.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import id.shaderboi.cata.feature_todo.ui.util.LocalTheme
import id.shaderboi.cata.ui.theme.Theme

enum class ToDoPriority {
    None,
    Low,
    Medium,
    High;

    override fun toString(): String {
        return this.name
    }

    val color
        @Composable
        get() = when (this) {
            ToDoPriority.High -> Color(0xfff44336)
            ToDoPriority.Medium -> Color(0xffffea00)
            ToDoPriority.Low -> Color(0xff00b0ff)
            else ->
                if (LocalTheme.current == Theme.Light)
                    Color.Black
                else
                    Color.White
        }
}
