package com.canonal.canvascompose.weight_picker

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WeightSelectionScreen() {
    var weight by remember { mutableStateOf(80) }
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            text = "Select your weight",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 64.dp),
            fontSize = 30.sp,
            fontWeight = FontWeight.Light
        )
        Text(
            text = "$weight KG",
            fontSize = 55.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Center)
        )
        Scale(
            style = ScaleStyle(scaleWidth = 150.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .align(Alignment.BottomCenter),
            onWeightChange = { currentWeight ->
                weight = currentWeight
            }
        )
    }
}