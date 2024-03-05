package sp.ax.jc.squares

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

data class SquaresStyle(
    val color: Color,
    val squareSize: DpSize,
    val paddingOffset: DpOffset,
    val cornerRadius: Dp,
    val backgroundContext: CoroutineContext,
)

val LocalSquaresStyle = staticCompositionLocalOf {
    SquaresStyle(
        color = Color.Black,
        squareSize = DpSize(width = 32.dp, height = 32.dp),
        paddingOffset = DpOffset(x = 16.dp, y = 16.dp),
        cornerRadius = 8.dp,
        backgroundContext = Dispatchers.Default,
    )
}
