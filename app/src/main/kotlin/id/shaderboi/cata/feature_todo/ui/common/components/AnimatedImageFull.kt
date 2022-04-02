package id.shaderboi.cata.feature_todo.ui.common.components

import androidx.annotation.RawRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import id.shaderboi.cata.R

@Composable
fun AnimatedImageFull(@RawRes resId: Int, title: String? = null, subtitle: String? = null) {
    val emptyComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(resId))
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxHeight()
            .padding(horizontal = 20.dp)
    ) {
        LottieAnimation(
            emptyComposition,
            iterations = LottieConstants.IterateForever,
            modifier = Modifier.height(300.dp),
        )
        title?.let { title ->
            Text(
                title,
                fontWeight = FontWeight.SemiBold,
                fontSize = MaterialTheme.typography.h5.fontSize,
                textAlign = TextAlign.Center
            )
        }
        subtitle?.let { subtitle ->
            Text(
                subtitle,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
@Preview
fun AnimatedImageFullPreview() {
    AnimatedImageFull(
        R.raw.empty,
        "There's nothing here",
        "You can add a To Do by pressing the + button on the corner"
    )
}
