package com.canonal.canvascompose.clock

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.time.Duration.Companion.seconds

data class Time(val hour: Int, val minute: Int, val second: Int)

@Composable
fun ClockWall() {
    var currentTime by remember { mutableStateOf(LocalDateTime.now()) }

    LaunchedEffect(true) {
        flow {
            while (true) {
                emit(LocalDateTime.now())
                delay(1.seconds)
            }
        }.collect { newTime ->
            currentTime = newTime
        }
    }
    val time = formatTime(currentTime)
    Box(modifier = Modifier.fillMaxSize()) {
        Clock(
            modifier = Modifier.align(Alignment.Center),
            hour = time.hour,
            minute = time.minute,
            second = time.second
        )
    }
}

private fun formatTime(time: LocalDateTime): Time {
    val hour = time.format(DateTimeFormatter.ofPattern("HH")).toInt()
    val minute = time.format(DateTimeFormatter.ofPattern("mm")).toInt()
    val second = time.format(DateTimeFormatter.ofPattern("ss")).toInt()
    return Time(hour, minute, second)
}
