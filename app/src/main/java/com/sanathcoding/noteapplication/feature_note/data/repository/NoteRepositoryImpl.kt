package com.sanathcoding.noteapplication.feature_note.data.repository

import com.sanathcoding.noteapplication.feature_note.data.data_source.NoteDao
import com.sanathcoding.noteapplication.feature_note.data.mapper.toNote
import com.sanathcoding.noteapplication.feature_note.data.mapper.toNoteEntity
import com.sanathcoding.noteapplication.feature_note.domain.model.Note
import com.sanathcoding.noteapplication.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val dao: NoteDao
): NoteRepository {
    override fun getNotes(): Flow<List<Note>> {
        return dao.getAllNotes().map { noteEntityList ->
            noteEntityList.map {
                it.toNote()
            }
        }
    }

    override suspend fun getNoteById(id: Int): Note? {
        return dao.getNoteById(id)?.toNote()
    }

    override suspend fun insertNote(note: Note) {
        return dao.insertNote(note.toNoteEntity())
    }

    override suspend fun deleteNote(note: Note) {
        return dao.deleteNote(note.toNoteEntity())
    }
}