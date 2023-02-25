package com.sanathcoding.noteapplication.feature_note.presentation.navigation

sealed class Screen(val route: String) {
    object NoteScreen: Screen("note_screen")
    object AddEditNoteScreen: Screen("add_edit_note_screen")
}
