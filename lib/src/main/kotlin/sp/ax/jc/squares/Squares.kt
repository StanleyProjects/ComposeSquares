package sp.ax.jc.squares

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Squares(
    color: Color = LocalSquaresStyle.current.color,
) {
    Box(modifier = Modifier.size(32.dp).background(color))
}
