package id.shaderboi.cata.feature_todo.ui.todo.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// Mostly coppied from androidx.compose.material.BottomAppBar
private val AppBarHeight = 56.dp
private val AppBarHorizontalPadding = 4.dp

// Start inset for the title when there is no navigation icon provided
private val TitleInsetWithoutIcon = Modifier.width(16.dp - AppBarHorizontalPadding)

// Start inset for the title when there is a navigation icon provided
private val TitleIconModifier = Modifier
    .fillMaxHeight()
    .width(72.dp - AppBarHorizontalPadding)

@Composable
fun BottomAppBar(
    navigationIcon: @Composable () -> Unit,
    content: @Composable () -> Unit
) = androidx.compose.material.BottomAppBar(
    cutoutShape = RoundedCornerShape(50.dp),
) {
    Row(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            TitleIconModifier,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.high) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(AppBarDefaults.ContentPadding)
                        .height(AppBarHeight),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    navigationIcon()
                }
            }
        }
        content()
    }
}

@Preview
@Composable
private fun BottomAppBarPreview() {
    BottomAppBar(
        navigationIcon = {}
    ) {}
}
