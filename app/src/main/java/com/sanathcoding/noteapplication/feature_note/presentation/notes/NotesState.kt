package com.sanathcoding.noteapplication.feature_note.presentation.notes

import com.sanathcoding.noteapplication.feature_note.domain.model.Note
import com.sanathcoding.noteapplication.feature_note.domain.util.NoteOrder
import com.sanathcoding.noteapplication.feature_note.domain.util.OrderType

data class NotesState(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.ByDate(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)
