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
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.unit.dp

@Composable
fun TransformationAndClipping() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        // pushes 300 from left -> it moves to right
        // pushes 300 from top -> it moves to bottom
        translate(left = 300f, top = 300f) {
            rotate(degrees = 45f, pivot = Offset(100f, 100f)) {
                scale(scale = 0.5f, pivot = Offset(200f, 200f)) {
                    drawRect(
                        color = Color.Green,
                        topLeft = Offset(100f, 100f),
                        size = Size(200f, 200f)
                    )
                }
            }
        }

        val circle = Path().apply {
            addOval(
                Rect(
                    center = Offset(400f, 400f),
                    radius = 300f
                )
            )
        }
        drawPath(
            path = circle,
            color = Color.Black,
            style = Stroke(width = 5.dp.toPx())
        )
        // inside of the circle ( path= circle) will be drawn
        clipPath(
            path = circle
        ) {
            drawRect(
                color = Color.Red,
                topLeft = Offset(400f, 400f),
                size = Size(400f, 400f)
            )
        }
    }
}