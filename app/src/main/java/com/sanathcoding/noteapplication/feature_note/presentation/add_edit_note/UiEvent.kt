package com.sanathcoding.noteapplication.feature_note.presentation.add_edit_note

sealed interface UiEvent{
    data class ShowSnackBar(val message: String): UiEvent
    object SaveNote: UiEvent
}