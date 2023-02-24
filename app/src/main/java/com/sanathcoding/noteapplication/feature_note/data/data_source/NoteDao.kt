package com.sanathcoding.noteapplication.feature_note.data.data_source

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM noteentity")
    fun getAllNotes(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM noteentity WHERE id = :id")
    suspend fun getNoteById(id: Int): NoteEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(noteEntity: NoteEntity)

    @Delete
    suspend fun deleteNote(noteEntity: NoteEntity)

}