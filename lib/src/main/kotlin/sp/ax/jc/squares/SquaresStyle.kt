package sp.ax.jc.squares

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp

data class SquaresStyle(
    val color: Color,
    val squareSize: DpSize,
)

val LocalSquaresStyle = staticCompositionLocalOf {
    SquaresStyle(
        color = Color.Black,
        squareSize = DpSize(width = 32.dp, height = 32.dp),
    )
}
