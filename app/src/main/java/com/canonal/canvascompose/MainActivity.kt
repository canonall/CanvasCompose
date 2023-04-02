package com.canonal.canvascompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.canonal.canvascompose.clock.ClockWall
import com.canonal.canvascompose.gender_picker.GenderPicker
import com.canonal.canvascompose.path.*

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
            //AnimatePath()
            //TransformationAndClipping()
            //PathEffects()
            //TextOnPath()
            GenderPicker(
                modifier = Modifier.fillMaxSize(),
                onGenderSelected = { gender ->
                    // do something with gender
                }
            )
        }
    }
}
