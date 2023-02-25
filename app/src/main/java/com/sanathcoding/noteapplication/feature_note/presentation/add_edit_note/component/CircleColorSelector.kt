package com.sanathcoding.noteapplication.feature_note.presentation.add_edit_note.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector4D
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sanathcoding.noteapplication.feature_note.presentation.add_edit_note.AddEditNoteEvent
import com.sanathcoding.noteapplication.feature_note.presentation.add_edit_note.AddEditNoteViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun CircleColorSelector(
    color: Color,
    addEditNoteViewModel: AddEditNoteViewModel,
    colorInt: Int,
    scope: CoroutineScope,
    backgroundAnimation: Animatable<Color, AnimationVector4D>
) {
    Box(
        modifier = Modifier
            .size(30.dp)
            .shadow(30.dp)
            .clip(CircleShape)
            .background(color)
            .border(
                width = 3.dp,
                color =
                if (addEditNoteViewModel.noteColor == colorInt) Color.Black
                else Color.Transparent,
                shape = CircleShape
            )
            .clickable {
                scope.launch {
                    backgroundAnimation.animateTo(
                        targetValue = Color(colorInt),
                        animationSpec = tween(
                            durationMillis = 300,
                            easing = LinearEasing
                        )
                    )
                }
                addEditNoteViewModel.onEvent(AddEditNoteEvent.ChangeColor(colorInt))
            }
    )
}