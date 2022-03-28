package id.shaderboi.cata.feature_todo.ui.common.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun <T> SelectDialog(
    selectedIndex: Int,
    options: Array<T>,
    title: (@Composable () -> Unit)? = null,
    onDismissRequest: () -> Unit = {},
    onClick: (idx: Int, selectedIdx: Int, option: T) -> Unit,
    itemComponent: (@Composable (idx: Int, selectedIdx: Int, option: T, onItemClick: () -> Unit) -> Unit) =
        { idx, selectedIdx, option, onItemClick ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onItemClick() }
                    .padding(horizontal = 10.dp, vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = idx == selectedIdx,
                    onClick = onItemClick
                )
                Text(
                    text = option.toString()
                )
            }
        }
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colors.background
        ) {
            Column {
                Box(
                    modifier = Modifier.padding(
                        start = 20.dp,
                        top = 20.dp,
                        end = 20.dp,
                        bottom = 10.dp
                    )
                ) {
                    title?.let {
                        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.high) {
                            val textStyle = MaterialTheme.typography.subtitle1
                            ProvideTextStyle(textStyle, title)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(5.dp))
                LazyColumn {
                    items(options.size) { idx ->
                        itemComponent(idx, selectedIndex, options[idx]) {
                            onClick(idx, selectedIndex, options[idx])
                        }
                    }
                }
            }
        }
    }
}