package id.shaderboi.cata.feature_todo.ui.common.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Flag
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import id.shaderboi.cata.feature_todo.domain.model.ToDoPriority

@Composable
fun <T> SelectDialog(
    selectedIndex: Int,
    options: Array<T>,
    modifier: Modifier = Modifier,
    title: (@Composable () -> Unit)? = null,
    onDismissRequest: () -> Unit = {},
    onClick: (idx: Int, option: T) -> Unit = { _, _ -> },
    itemComponent: (@Composable (idx: Int, option: T, onItemClick: () -> Unit) -> Unit) =
        { idx, option, onItemClick ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onItemClick() }
                    .padding(horizontal = 10.dp, vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = idx == selectedIndex,
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
            color = MaterialTheme.colors.background,
            modifier = modifier
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
                        itemComponent(idx, options[idx]) {
                            onClick(idx, options[idx])
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun SelectDialogPreview() {
    SelectDialog(
        selectedIndex = 0,
        options = ToDoPriority.values(),
        title = {
            Text("Select priority", fontWeight = FontWeight.Medium, fontSize = 20.sp)
        },
        itemComponent = { idx, option, onItemClick ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onItemClick() }
                    .padding(horizontal = 10.dp, vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = idx == 0,
                    onClick = onItemClick
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = option.toString()
                    )
                    Icon(
                        imageVector = Icons.Default.Flag,
                        contentDescription = "Priority $option",
                        tint = option.color
                    )
                }
            }
        }
    )
}
