package id.shaderboi.cata.feature_todo.ui.home.subscreen.misc.components.preference

import androidx.compose.runtime.Composable

data class Preference(
    val title: String,
    val description: String?,
    val icon: @Composable () -> Unit = {},
    val enabled: Boolean = true
)