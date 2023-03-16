package com.canonal.canvascompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sqrt
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //MyCanvas()
            MainScreen()
        }
    }
}

@Composable
fun MainScreen() {
    var points by remember { mutableStateOf(0) }
    var isTimerRunning by remember { mutableStateOf(false) }
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Points: $points",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Button(
                onClick = {
                    isTimerRunning = !isTimerRunning
                    points = 0
                }
            ) {
                Text(text = if (isTimerRunning) "Reset" else "Start")
            }
            CountDownTimer(isTimerRunning = isTimerRunning,
                onTimerEnd = {
                    isTimerRunning = false
                }
            )
        }
        BallClicker(
            enabled = isTimerRunning,
            onBallClick = {
                points++
            }
        )
    }
}

@Composable
fun CountDownTimer(
    time: Int = 30_000,
    isTimerRunning: Boolean = false,
    onTimerEnd: () -> Unit = {}
) {
    var currentTime by remember { mutableStateOf(time) }

    LaunchedEffect(key1 = currentTime, key2 = isTimerRunning) {
        if (!isTimerRunning) {
            currentTime = time
            return@LaunchedEffect
        }
        if (currentTime > 0) {
            delay(1000L)
            currentTime -= 1000
        } else {
            onTimerEnd()
        }
    }

    Text(
        text = (currentTime / 1000).toString(),
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun BallClicker(
    radius: Float = 100f,
    enabled: Boolean = false,
    ballColor: Color = Color.Red,
    onBallClick: () -> Unit = {}
) {
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        var ballCenter by remember {
            mutableStateOf(
                randomOffset(
                    radius = radius,
                    width = constraints.maxWidth,
                    height = constraints.maxHeight
                )
            )
        }
        Canvas(modifier = Modifier
            .fillMaxSize()
            .pointerInput(key1 = enabled) {
                if (!enabled) {
                    return@pointerInput
                }
                detectTapGestures { clickOffset ->
                    val distanceFromCenter = sqrt(
                        (clickOffset.x - ballCenter.x).pow(2) +
                                (clickOffset.y - ballCenter.y).pow(2)
                    )
                    if (distanceFromCenter <= radius) {
                        // clicked inside the ball
                        // generate new ball position
                        ballCenter = randomOffset(
                            radius = radius,
                            width = constraints.maxWidth,
                            height = constraints.maxHeight
                        )
                        onBallClick()
                    }
                }
            }

        ) {
            drawCircle(
                color = ballColor,
                radius = radius,
                center = ballCenter
            )
        }
    }
}

private fun randomOffset(radius: Float, width: Int, height: Int): Offset {
    return Offset(
        x = Random.nextInt(
            from = radius.roundToInt(),
            until = width - radius.roundToInt()
        ).toFloat(),
        y = Random.nextInt(
            from = radius.roundToInt(),
            until = height - radius.roundToInt()
        ).toFloat()
    )
}

@Composable
fun MyCanvas() {
    Canvas(
        modifier = Modifier
            .padding(20.dp)
            .size(300.dp)
    ) {
        drawRect(
            color = Color.Black,
            size = size
        )
        drawRect(
            color = Color.Red,
            topLeft = Offset(x = 100f, y = 100f),
            size = Size(width = 100f, height = 100f),
            style = Stroke(
                width = 3.dp.toPx()
            )
        )
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(Color.Red, Color.Yellow),
                center = center,
                radius = 100f
            ),
            radius = 100f
        )
        drawArc(
            color = Color.Green,
            startAngle = 0f,
            sweepAngle = 270f,
            useCenter = false,
            topLeft = Offset(x = 100f, y = 500f),
            size = Size(width = 200f, height = 200f),
            style = Stroke(
                width = 3.dp.toPx()
            )
        )
        drawOval(
            color = Color.Magenta,
            topLeft = Offset(x = 500f, y = 100f),
            size = Size(width = 200f, height = 300f)
        )
        drawLine(
            color = Color.Cyan,
            start = Offset(x = 300f, y = 700f),
            end = Offset(x = 700f, y = 700f),
            strokeWidth = 5.dp.toPx()
        )
    }
}