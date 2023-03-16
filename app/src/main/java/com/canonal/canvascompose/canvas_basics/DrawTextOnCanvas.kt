package com.canonal.canvascompose.canvas_basics

import android.graphics.Color
import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.nativeCanvas

@Composable
fun DrawTextOnCanvas() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        // naviteCanvas is what we use in XML
        drawContext.canvas.nativeCanvas.apply {
            drawText(
                 "This is my text",
                 100f,
                 100f,
                Paint().apply {
                    color = Color.RED
                    textSize = 100f
                }
            )
        }
    }
}