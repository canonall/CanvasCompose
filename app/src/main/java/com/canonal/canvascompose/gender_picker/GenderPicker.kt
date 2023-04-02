package com.canonal.canvascompose.gender_picker


import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.canonal.canvascompose.R

@Composable
fun GenderPicker(
    modifier: Modifier = Modifier,
    maleGradient: List<Color> = listOf(Color(0xFF6D6DFF), Color.Blue),
    femaleGradient: List<Color> = listOf(Color(0xFFEA76FF), Color.Magenta),
    distanceBetweenGenders: Dp = 50.dp,
    pathScaleFactor: Float = 7f,
    onGenderSelected: (Gender) -> Unit
) {
    var selectedGender by remember { mutableStateOf<Gender>(Gender.Female) }
    var center by remember { mutableStateOf(Offset.Unspecified) }
    var currentClickOffset by remember { mutableStateOf(Offset.Zero) }
    val maleSelectionRadius = animateFloatAsState(
        targetValue = if (selectedGender is Gender.Male) 80f else 0f,
        animationSpec = tween(durationMillis = 500)
    )
    val femaleSelectionRadius = animateFloatAsState(
        targetValue = if (selectedGender is Gender.Female) 80f else 0f,
        animationSpec = tween(durationMillis = 500)
    )

    val malePathString = stringResource(id = R.string.male_path)
    val malePath = remember { PathParser().parsePathString(malePathString).toPath() }
    val malePathBounds = remember { malePath.getBounds() }
    var maleTranslationOffset by remember { mutableStateOf(Offset.Zero) }

    val femalePathString = stringResource(id = R.string.female_path)
    val femalePath = remember { PathParser().parsePathString(femalePathString).toPath() }
    val femalePathBounds = remember { femalePath.getBounds() }
    var femaleTranslationOffset by remember { mutableStateOf(Offset.Zero) }

    Canvas(
        modifier = modifier
            .pointerInput(true) {
                detectTapGestures { clickPosition ->
                    val transformedMaleRect = Rect(
                        offset = maleTranslationOffset,
                        size = malePathBounds.size * pathScaleFactor
                    )
                    val transformedFemaleRect = Rect(
                        offset = femaleTranslationOffset,
                        size = femalePathBounds.size * pathScaleFactor
                    )

                    if (selectedGender !is Gender.Male && transformedMaleRect.contains(clickPosition)) {
                        // clicked male
                        currentClickOffset = clickPosition
                        selectedGender = Gender.Male
                        onGenderSelected(Gender.Male)
                    } else if (selectedGender !is Gender.Female && transformedFemaleRect.contains(
                            clickPosition
                        )
                    ) {
                        // clicked female
                        currentClickOffset = clickPosition
                        selectedGender = Gender.Female
                        onGenderSelected(Gender.Female)
                    }
                }
            }
    ) {
        center = this.center

        maleTranslationOffset = Offset(
            x = (center.x - (malePathBounds.width * pathScaleFactor) - (distanceBetweenGenders.toPx() / 2f)),
            y = (center.y - (pathScaleFactor * malePathBounds.height / 2f))
        )

        femaleTranslationOffset = Offset(
            x = (center.x + (distanceBetweenGenders.toPx() / 2f)),
            y = (center.y - (pathScaleFactor * femalePathBounds.height / 2f))
        )

        // start the circle wherever we click after initialization (first time we click)
        // we need to undo so that the click position is not transformed and scaled again
        val untransformedMaleClickOffset = if (currentClickOffset == Offset.Zero) {
            malePathBounds.center
        } else {
            // undo translation and undo scaling
            ((currentClickOffset - maleTranslationOffset) / pathScaleFactor)
        }
        val untransformedFemaleClickOffset = if (currentClickOffset == Offset.Zero) {
            femalePathBounds.center
        } else {
            // undo translation and undo scaling
            ((currentClickOffset - femaleTranslationOffset) / pathScaleFactor)
        }

        translate(
            left = maleTranslationOffset.x,
            top = maleTranslationOffset.y
        ) {
            scale(
                scale = pathScaleFactor,
                pivot = malePathBounds.topLeft
            ) {
                drawPath(
                    path = malePath,
                    color = Color.LightGray
                )
                clipPath(
                    path = malePath
                ) {
                    // will be drawn inside the malePath
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = maleGradient,
                            center = untransformedMaleClickOffset,
                            // crashed when 0 so add 1
                            radius = maleSelectionRadius.value + 1
                        ),
                        radius = maleSelectionRadius.value,
                        center = untransformedMaleClickOffset
                    )
                }
            }
        }

        translate(
            left = femaleTranslationOffset.x,
            top = femaleTranslationOffset.y
        ) {
            scale(
                scale = pathScaleFactor,
                pivot = femalePathBounds.topLeft
            ) {
                drawPath(
                    path = femalePath,
                    color = Color.LightGray
                )
                clipPath(
                    path = femalePath
                ) {
                    // will be drawn inside the femalePath
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = femaleGradient,
                            center = untransformedFemaleClickOffset,
                            // crashed when 0 so add 1
                            radius = femaleSelectionRadius.value + 1
                        ),
                        radius = femaleSelectionRadius.value,
                        center = untransformedFemaleClickOffset
                    )
                }
            }
        }
    }
}