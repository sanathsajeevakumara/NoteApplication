package com.sanathcoding.noteapplication.feature_note.domain.model

import androidx.compose.ui.graphics.Color
import com.sanathcoding.noteapplication.ui.theme.*

data class Note(
    val title: String,
    val content: String,
    val timeStamp: Long,
    val color: Color,
    val id: Int? = null
) {
    companion object {
        val noteColor = listOf(
            RedOrange,
            LightGreen,
            Violet,
            BabyBlue,
            RedPink
        )
    }
}
