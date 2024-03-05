package sp.ax.jc.squares

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class SquaresStyle(
    val color: Color,
)

val LocalSquaresStyle = staticCompositionLocalOf {
    SquaresStyle(
        color = Color.Black,
    )
}
