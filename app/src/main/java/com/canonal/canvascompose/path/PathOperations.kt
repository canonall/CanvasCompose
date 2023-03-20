package com.canonal.canvascompose.path

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun PathOperations() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val squareWithoutOp = Path().apply {
            addRect(
                Rect(
                    offset = Offset(200f, 200f),
                    size = Size(200f, 200f)
                )
            )
        }
        val circle = Path().apply {
            addOval(
                Rect(
                    center = Offset(200f,200f),
                    radius = 100f
                )
            )
        }
        val pathWithOperation = Path().apply {
            op(
                path1 = squareWithoutOp,
                path2 = circle,
                operation = PathOperation.Difference
            )
        }
        drawPath(
            path = squareWithoutOp,
            color = Color.Red,
            style = Stroke(width = 2.dp.toPx())
        )
        drawPath(
            path = circle,
            color = Color.Blue,
            style = Stroke(width = 2.dp.toPx())
        )
        drawPath(
            path = pathWithOperation,
            color = Color.Green
        )
    }
}