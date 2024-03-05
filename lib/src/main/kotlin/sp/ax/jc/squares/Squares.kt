package sp.ax.jc.squares

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.DpSize
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

private fun Float.ct(k: Float): Float {
    return (this % k + k) % k
}

@Composable
fun Squares(
    modifier: Modifier = Modifier,
    color: Color = LocalSquaresStyle.current.color,
    squareSize: DpSize = LocalSquaresStyle.current.squareSize,
    paddingOffset: DpOffset = LocalSquaresStyle.current.paddingOffset,
    cornerRadius: Dp = LocalSquaresStyle.current.cornerRadius,
    backgroundContext: CoroutineContext = LocalSquaresStyle.current.backgroundContext,
) {
    Box(modifier = modifier) {
        val alphaState = remember { mutableFloatStateOf(1f) }
        Canvas(
            modifier = Modifier
                .size(
                    DpSize(
                        width = paddingOffset.x + squareSize.width * 2,
                        height = paddingOffset.y + squareSize.height * 2),
                ),
        ) {
            val size = squareSize.toSize()
            val corners = CornerRadius(x = cornerRadius.toPx(), y = cornerRadius.toPx())
            drawRoundRect(
                color = color.copy(alpha = (alphaState.floatValue - 0.00f).ct(1f)), // todo
                topLeft = Offset.Zero,
                size = size,
                cornerRadius = corners,
            )
            drawRoundRect(
                color = color.copy(alpha = (alphaState.floatValue - 0.25f).ct(1f)),
                topLeft = Offset.Zero.copy(x = size.width + paddingOffset.x.toPx()),
                size = size,
                cornerRadius = corners,
            )
        }
        LaunchedEffect(alphaState.floatValue) {
            withContext(backgroundContext) {
                delay(16)
            }
            alphaState.floatValue = (alphaState.floatValue + 0.025f).ct(1f)
        }
    }
}
