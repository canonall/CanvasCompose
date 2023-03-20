package com.canonal.canvascompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.canonal.canvascompose.clock.ClockWall
import com.canonal.canvascompose.path.AnimatePath
import com.canonal.canvascompose.path.PathBasics
import com.canonal.canvascompose.path.PathOperations

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //MyCanvas()
            //MainScreen()
            //DrawTextOnCanvas()
            //WeightSelectionScreen()
            //ClockWall()
            //PathBasics()
            //PathOperations()
            AnimatePath()
        }
    }
}
