package com.canonal.canvascompose.path

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StampedPathEffectStyle
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun PathEffects() {
    val infiniteTransition = rememberInfiniteTransition()
    val phase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 10000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 60_000,
                easing = LinearEasing
            )
        )
    )
    Canvas(modifier = Modifier.fillMaxSize()) {
        val path = Path().apply {
            moveTo(100f, 100f)
            cubicTo(
                x1 = 100f,
                y1 = 300f,
                x2 = 600f,
                y2 = 700f,
                x3 = 600f,
                y3 = 1100f
            )
            lineTo(800f, 800f)
            lineTo(1000f, 1100f)
        }
        val oval = Path().apply {
            addOval(
                Rect(
                    topLeft = Offset.Zero,
                    bottomRight = Offset(40f, 10f)
                )
            )
        }
        drawPath(
            path = path,
            color = Color.Red,
            style = Stroke(
                width = 5.dp.toPx(),
                //--------------------------------------------
                // dashPathEffect
//                pathEffect = PathEffect.dashPathEffect(
//                    // first item is the length of a dash -> "-"
//                    // second item is the distance between each dash"-"
//                    intervals = floatArrayOf(50f, 30f),
//                    phase = phase
//                )
                //--------------------------------------------
                // cornerPathEffect
                //pathEffect = PathEffect.cornerPathEffect(radius = 1000f)
                //--------------------------------------------
                // stampedPathEffect
                // shape will be drawn along the path (the one in the initial drawPath)
                pathEffect = PathEffect.stampedPathEffect(
                    shape = oval,
                    advance = 100f, // interval between stamps (distance between ovals)
                    phase = phase,
                    style = StampedPathEffectStyle.Morph
                )
                //--------------------------------------------
                // chainPathEffect
                // combines paths
//                pathEffect = PathEffect.chainPathEffect(
//                    outer = PathEffect.stampedPathEffect(
//                        shape = oval,
//                        advance = 30f,
//                        phase = 0f,
//                        style = StampedPathEffectStyle.Rotate
//                    ),
//                    inner = PathEffect.dashPathEffect(
//                        intervals = floatArrayOf(100f, 100f)
//                    )
//                )
            )
        )
    }
}