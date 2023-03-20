package com.canonal.canvascompose.path

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun PathBasics() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        // quadraticBezier
        val path = Path().apply {
            moveTo(100f, 100f)
            lineTo(100f, 500f)
            lineTo(500f, 500f)
            //lineTo(500f, 100f)
            // quadraticBezier can not create perfect curves
            quadraticBezierTo(
                // x1, y1 -> control point
                x1 = 800f,
                y1 = 300f,
                // x2,y2 -> connection point just as lineTo(x,y)
                x2 = 500f,
                y2 = 100f
            )
            close()
        }
        drawPath(
            path = path,
            color = Color.Red,
            style = Stroke(width = 5.dp.toPx())
        )

        // cubic
        val pathCubic = Path().apply {
           // moveTo(100f, 900f)
            moveTo(1000f, 900f)
            lineTo(100f, 1300f)
            lineTo(500f, 1300f)
            // x1,y1 -> control point
            // x2,y2 -> control point
            // x3,y3 -> connection point
            cubicTo(
                x1 = 800f,
                y1 = 1300f,
                x2 = 800f,
                y2 = 900f,
                x3 = 500f,
                y3 = 900f
            )

        }
        drawPath(
            path = pathCubic,
            color = Color.Red,
            style = Stroke(
                width = 10.dp.toPx(),
                cap = StrokeCap.Round,
                join = StrokeJoin.Miter,
                miter = 20f
            )
        )
    }
}