package id.shaderboi.cata.feature_todo.ui.home.subscreen.misc.components

import androidx.compose.foundation.clickable
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import id.shaderboi.cata.feature_todo.ui.home.subscreen.misc.components.local.LocalPreferenceEnabledStatus
import id.shaderboi.cata.feature_todo.ui.home.subscreen.misc.components.preference.Preference

@Composable
internal fun TextPreferenceWidget(
    preference: Preference,
    onClick: () -> Unit = { },
    trailing: @Composable (() -> Unit)? = null
) {
    val isEnabled = LocalPreferenceEnabledStatus.current && preference.enabled
    StatusWrapper(enabled = isEnabled) {
        MaterialListItem(
            text = {
                Text(
                    text = preference.title,
//                    maxLines = if (preference.singleLineTitle) 1 else Int.MAX_VALUE
                )
            },
            secondaryText = {
                preference.description?.let { description ->
                    Text(text = description)
                }
            },
            icon = preference.icon,
            modifier = Modifier.clickable(onClick = { if (isEnabled) onClick() }),
            trailing = trailing,
            singleLineSecondaryText = false
        )
    }
}
