package com.canonal.canvascompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.canonal.canvascompose.canvas_basics.DrawTextOnCanvas
import com.canonal.canvascompose.canvas_basics.MainScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //MyCanvas()
            //MainScreen()
            DrawTextOnCanvas()
        }
    }
}
