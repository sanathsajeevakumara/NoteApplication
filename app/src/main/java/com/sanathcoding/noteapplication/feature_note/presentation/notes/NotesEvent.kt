package com.sanathcoding.noteapplication.feature_note.presentation.notes

import com.sanathcoding.noteapplication.feature_note.domain.model.Note
import com.sanathcoding.noteapplication.feature_note.domain.util.NoteOrder

sealed interface NotesEvent {
    data class OrderNote(val noteOrder: NoteOrder): NotesEvent
    data class DeleteNote(val note: Note): NotesEvent
    object RestoreNote: NotesEvent
    object ToggleOrder: NotesEvent
}
