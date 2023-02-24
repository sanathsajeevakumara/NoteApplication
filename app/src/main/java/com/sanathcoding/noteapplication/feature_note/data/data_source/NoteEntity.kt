package com.sanathcoding.noteapplication.feature_note.data.data_source

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NoteEntity(
    val title: String,
    val content: String,
    val timeStamp: Long,
    val color: Color,
    @PrimaryKey val id: Int? = null
)
