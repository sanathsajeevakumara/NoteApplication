package com.sanathcoding.noteapplication.feature_note.data.mapper

import com.sanathcoding.noteapplication.feature_note.data.data_source.NoteEntity
import com.sanathcoding.noteapplication.feature_note.domain.model.Note

fun NoteEntity.toNote(): Note {
    return Note(
        title = title,
        content = content,
        timeStamp = timeStamp,
        color = color,
        id = id
    )
}

fun Note.toNoteEntity(): NoteEntity {
    return NoteEntity(
        title = title,
        content = content,
        timeStamp = timeStamp,
        color = color,
        id = id
    )
}