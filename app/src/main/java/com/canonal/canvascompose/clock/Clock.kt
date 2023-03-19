package com.canonal.canvascompose.clock

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

data class ClockStyle(
    val radius: Dp = 120.dp,
    val clockBackgroundColor: Color = Color.White,
    val hourLineColor: Color = Color.Black,
    val minuteLineColor: Color = Color.LightGray,
    val hourLineLength: Dp = 22.dp,
    val minuteLineLength: Dp = 15.dp,
    val hourHandStroke: Float = 18f,
    val minuteHandStroke: Float = 12f,
    val secondHandStroke: Float = 8f,
    val hourHandColor: Color = Color.Black,
    val minuteHandColor: Color = Color.Black,
    val secondHandColor: Color = Color.Red
)

sealed class LineType {
    object HourLine : LineType()
    object MinuteLine : LineType()
}

const val startTime = 0
const val endTime = 59

@Composable
fun Clock(
    hour: Int,
    minute: Int,
    second: Int,
    modifier: Modifier = Modifier,
    style: ClockStyle = ClockStyle()
) {
    val radius = style.radius

    Canvas(modifier = modifier) {
        drawContext.canvas.nativeCanvas.apply {
            drawCircle(
                center.x,
                center.y,
                radius.toPx(),
                Paint().apply {
                    color = style.clockBackgroundColor.toArgb()
                    setShadowLayer(
                        60f,
                        0f,
                        0f,
                        android.graphics.Color.argb(50, 0, 0, 0)
                    )
                }
            )
        }
        // draw time lines
        for (time in startTime..endTime) {
            val angleInRadian = ((time * 6f - 90f) * (PI / 180f)).toFloat()
            val lineType = when {
                time % 5 == 0 -> LineType.HourLine
                else -> LineType.MinuteLine
            }
            val lineLength = when (lineType) {
                LineType.HourLine -> style.hourLineLength.toPx()
                LineType.MinuteLine -> style.minuteLineLength.toPx()
            }
            val lineColor = when (lineType) {
                LineType.HourLine -> style.hourLineColor
                LineType.MinuteLine -> style.minuteLineColor
            }
            // inside position of each line with Polar Coordinate
            val lineStart = Offset(
                x = (radius.toPx() - lineLength) * cos(angleInRadian),
                y = (radius.toPx() - lineLength) * sin(angleInRadian)
            )
            // outside position of each line with Polar Coordinate
            val lineEnd = Offset(
                x = radius.toPx() * cos(angleInRadian),
                y = radius.toPx() * sin(angleInRadian)
            )
            drawLine(
                color = lineColor,
                start = lineStart,
                end = lineEnd,
                strokeWidth = 1.dp.toPx()
            )
        }
        // draw minute hand
        val minuteHandLength = (radius - style.hourLineLength).toPx() - 40f
        val minuteAngleInRadian = ((minute * 6f - 90f) * (PI.toFloat() / 180f))
        val minuteHandStart = center
        val minuteHandEnd = Offset(
            x = minuteHandLength * cos(minuteAngleInRadian),
            y = minuteHandLength * sin(minuteAngleInRadian)
        )
        drawLine(
            color = style.minuteHandColor,
            start = minuteHandStart,
            end = minuteHandEnd,
            cap = StrokeCap.Round,
            strokeWidth = style.minuteHandStroke,
        )
        // draw hour hand
        val hourHandLength = minuteHandLength - 75f
        val hourAngleInRadian = ((hour * 30f - 90f) * (PI.toFloat() / 180f))
        val hourHandStart = center
        val hourHandEnd = Offset(
            x = hourHandLength * cos(hourAngleInRadian),
            y = hourHandLength * sin(hourAngleInRadian)
        )
        drawLine(
            color = style.hourHandColor,
            start = hourHandStart,
            end = hourHandEnd,
            cap = StrokeCap.Round,
            strokeWidth = style.hourHandStroke
        )
        // draw second hand
        // secondHand and minuteHand have the same length
        val secondAngleInRadian = (second * 6f - 90f) * (PI.toFloat() / 180f)
        val secondHandStart = center
        val secondHandEnd = Offset(
            x = minuteHandLength * cos(secondAngleInRadian),
            y = minuteHandLength * sin(secondAngleInRadian)
        )
        drawLine(
            color = style.secondHandColor,
            start = secondHandStart,
            end = secondHandEnd,
            cap = StrokeCap.Round,
            strokeWidth = style.secondHandStroke
        )
        // draw small circle on center
        drawCircle(
            color = Color.Black,
            radius = 14f,
            center = center
        )
    }
}