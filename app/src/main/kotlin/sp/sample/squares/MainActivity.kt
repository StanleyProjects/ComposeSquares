package sp.sample.squares

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import sp.ax.jc.squares.LocalSquaresStyle
import sp.ax.jc.squares.Squares
import sp.ax.jc.squares.SquaresStyle

internal class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = ComposeView(this)
        setContentView(view)
        view.setContent {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center),
                ) {
                    Squares(
                        modifier = Modifier
                            .weight(1f)
                            .wrapContentWidth(),
                    )
                    Squares(
                        modifier = Modifier
                            .weight(1f)
                            .wrapContentWidth(),
                        color = Color.Red,
                    )
                    CompositionLocalProvider(
                        LocalSquaresStyle provides SquaresStyle(
                            color = Color.Yellow,
                            squareSize = DpSize(width = 12.dp, height = 24.dp),
                        )
                    ) {
                        Squares(
                            modifier = Modifier
                                .weight(1f)
                                .wrapContentWidth(),
                        )
                    }
                }
            }
        }
    }
}
