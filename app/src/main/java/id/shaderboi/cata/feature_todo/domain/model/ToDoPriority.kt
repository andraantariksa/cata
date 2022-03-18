package id.shaderboi.cata.feature_todo.domain.model

import androidx.compose.ui.graphics.Color

enum class ToDoPriority {
    None,
    Low,
    Medium,
    High;

    override fun toString(): String {
        return this.name
    }

    val color
        get() = when (this) {
            ToDoPriority.High -> Color(0xfff44336)
            ToDoPriority.Medium -> Color(0xffffea00)
            ToDoPriority.Low -> Color(0xff00b0ff)
            else -> Color.Black
        }
}
