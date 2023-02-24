package com.sanathcoding.noteapplication.feature_note.domain.use_case

import android.app.Application
import com.sanathcoding.noteapplication.R
import com.sanathcoding.noteapplication.core.common.InvalidNoteException
import com.sanathcoding.noteapplication.feature_note.domain.model.Note
import com.sanathcoding.noteapplication.feature_note.domain.repository.NoteRepository
import javax.inject.Inject

class AddNote @Inject constructor(
    private val repository: NoteRepository,
    private val application: Application
) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {
        if (note.title.isBlank()) throw InvalidNoteException(
            application.getString(R.string.note_title_empty_error)
        )
        if (note.content.isBlank()) throw InvalidNoteException(
            application.getString(R.string.note_content_empty_error)
        )
        repository.insertNote(note)
    }

}