package sp.ax.jc.squares

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp

private fun Float.ct(k: Float): Float {
    return (this % k + k) % k
}

@Composable
fun Squares(
    modifier: Modifier = Modifier,
    color: Color = LocalSquaresStyle.current.color,
    squareSize: DpSize = LocalSquaresStyle.current.squareSize,
) {
    Box(modifier = modifier) {
        val alphaState = remember { mutableFloatStateOf(1f) }
        val size = DpSize(width = squareSize.width * 2, height = squareSize.height * 2)
//        val size = DpSize(width = width + padding + width, height = width + padding + width) // todo
//        val cornerRadius = CornerRadius(x = radius.toPx(), y = radius.toPx()) // todo
        Canvas(
            modifier = Modifier.size(size),
        ) {
            val cornerRadius = CornerRadius(x = 8.dp.toPx(), y = 8.dp.toPx())
            drawRoundRect(
                color = color,
//                color = color.copy(alpha = (alphaState.floatValue - 0.00f).ct(1f)), // todo
                topLeft = Offset.Zero,
                size = squareSize.toSize(),
                cornerRadius = cornerRadius,
            )
        }
    }
}
