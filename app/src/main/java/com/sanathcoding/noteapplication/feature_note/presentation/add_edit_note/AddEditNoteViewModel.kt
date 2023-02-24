package com.sanathcoding.noteapplication.feature_note.presentation.add_edit_note

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sanathcoding.noteapplication.R
import com.sanathcoding.noteapplication.core.common.InvalidNoteException
import com.sanathcoding.noteapplication.feature_note.domain.model.Note
import com.sanathcoding.noteapplication.feature_note.domain.use_case.NoteUseCases
import com.sanathcoding.noteapplication.feature_note.presentation.add_edit_note.component.NoteTextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    private val application: Application,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    var noteTitle by mutableStateOf(
        NoteTextFieldState(
            hint = application.getString(R.string.title_hint)
        )
    )

    var noteContent by mutableStateOf(
        NoteTextFieldState(
            hint = application.getString(R.string.content_hint)
        )
    )

    var noteColor by mutableStateOf(Note.noteColor.random().toArgb())

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private var currentId: Int? = null

    init {
        savedStateHandle.get<Int>("noteId")?.let { noteId ->
            if (noteId != -1) {
                viewModelScope.launch(Dispatchers.IO) {
                    noteUseCases.getNote(noteId)?.also {note ->
                        currentId = note.id
                        noteTitle = noteTitle.copy(
                            text = note.title,
                            isHintVisible = false
                        )
                        noteContent = noteContent.copy(
                            text = note.content,
                            isHintVisible = false
                        )
                        noteColor = note.color
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditNoteEvent) {
        when(event) {
            is AddEditNoteEvent.EnteredTitle -> {
                noteTitle = noteTitle.copy(
                    text = event.title,
                )
            }
            is AddEditNoteEvent.ChangeTitleFocus -> {
                noteTitle = noteTitle.copy(
                    isHintVisible = !event.focusState.isFocused
                            && noteTitle.text.isBlank()
                )
            }
            is AddEditNoteEvent.EnteredContent -> {
                noteContent = noteContent.copy(
                    text = event.content
                )
            }
            is AddEditNoteEvent.ChangeContentFocus -> {
                noteContent = noteContent.copy(
                    isHintVisible = noteContent.text.isBlank()
                            && !event.focusState.isFocused
                )
            }
            is AddEditNoteEvent.ChangeColor -> {
                noteColor = event.color
            }
            AddEditNoteEvent.SaveNote -> {
                viewModelScope.launch {
                    try {

                        noteUseCases.addNote(
                            Note(
                                title = noteTitle.text,
                                content = noteContent.text,
                                timeStamp = System.currentTimeMillis(),
                                color = noteColor,
                                id = currentId
                            )
                        )
                        _uiEvent.emit(UiEvent.SaveNote)

                    } catch (e: InvalidNoteException) {
                        _uiEvent.emit(
                            UiEvent.ShowSnackBar(
                                message = e.message ?: application.getString(R.string.can_not_save_note)
                            )
                        )
                    }
                }
            }
        }
    }

}