package com.sanathcoding.noteapplication.feature_note.domain.use_case

import com.sanathcoding.noteapplication.feature_note.domain.model.Note
import com.sanathcoding.noteapplication.feature_note.domain.repository.NoteRepository
import com.sanathcoding.noteapplication.feature_note.domain.util.NoteOrder
import com.sanathcoding.noteapplication.feature_note.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetNotes @Inject constructor(
    private val repository: NoteRepository
) {

    operator fun invoke(
        noteOrder: NoteOrder = NoteOrder.ByDate(OrderType.Descending)
    ): Flow<List<Note>> {
        return repository.getNotes().map { notes ->
            when (noteOrder.orderType) {
                OrderType.Ascending -> {
                    when (noteOrder) {
                        is NoteOrder.ByColor -> {
                            notes.sortedBy {
                                it.color
                            }
                        }
                        is NoteOrder.ByDate -> {
                            notes.sortedBy {
                                it.timeStamp
                            }
                        }
                        is NoteOrder.ByTitle -> {
                            notes.sortedBy {
                                it.title.lowercase()
                            }
                        }
                    }
                }
                OrderType.Descending -> {
                    when (noteOrder) {
                        is NoteOrder.ByColor -> {
                            notes.sortedByDescending {
                                it.color
                            }
                        }
                        is NoteOrder.ByDate -> {
                            notes.sortedByDescending {
                                it.timeStamp
                            }
                        }
                        is NoteOrder.ByTitle -> {
                            notes.sortedByDescending {
                                it.title.lowercase()
                            }
                        }
                    }
                }
            }
        }
    }

}