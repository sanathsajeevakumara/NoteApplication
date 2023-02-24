package com.sanathcoding.noteapplication.feature_note.presentation.notes

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sanathcoding.noteapplication.feature_note.domain.model.Note
import com.sanathcoding.noteapplication.feature_note.domain.use_case.NoteUseCases
import com.sanathcoding.noteapplication.feature_note.domain.util.NoteOrder
import com.sanathcoding.noteapplication.feature_note.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
): ViewModel() {

    var state by mutableStateOf(NotesState())

    private var recentlyDeletedNote: Note? = null

    private var getNotesJob: Job? = null

    init {
        getNotes(NoteOrder.ByDate(OrderType.Descending))
    }

    fun onEvent(event: NotesEvent) {
        when(event) {
            is NotesEvent.OrderNote -> {
                if (
                    state.noteOrder::class == event.noteOrder::class &&
                            state.noteOrder.orderType == event.noteOrder.orderType
                ) {
                    return
                } else {
                    getNotes(event.noteOrder)
                }
            }
            is NotesEvent.DeleteNote -> {
                viewModelScope.launch(Dispatchers.IO) {
                    noteUseCases.deleteNote(note = event.note)
                    recentlyDeletedNote = event.note
                }
            }
            NotesEvent.RestoreNote -> {
                viewModelScope.launch(Dispatchers.IO) {
                    noteUseCases.addNote(recentlyDeletedNote ?: return@launch)
                    recentlyDeletedNote = null
                }
            }
            NotesEvent.ToggleOrder -> {
                state = state.copy(
                    isOrderSectionVisible = !state.isOrderSectionVisible
                )
            }
        }
    }

    private fun getNotes(noteOrder: NoteOrder) {
        getNotesJob?.cancel()
        getNotesJob = noteUseCases.getNotes(noteOrder = noteOrder)
            .onEach {notes ->
                state = state.copy(
                    notes = notes,
                    noteOrder = noteOrder
                )
            }.launchIn(viewModelScope)
    }

}