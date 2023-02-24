package com.sanathcoding.noteapplication.feature_note.di

import android.app.Application
import androidx.room.Room
import com.sanathcoding.noteapplication.core.common.NoteConst.Companion.NOTE_DB_NAME
import com.sanathcoding.noteapplication.feature_note.data.data_source.NoteDao
import com.sanathcoding.noteapplication.feature_note.data.data_source.NoteDatabase
import com.sanathcoding.noteapplication.feature_note.domain.repository.NoteRepository
import com.sanathcoding.noteapplication.feature_note.domain.use_case.AddNote
import com.sanathcoding.noteapplication.feature_note.domain.use_case.DeleteNote
import com.sanathcoding.noteapplication.feature_note.domain.use_case.GetNotes
import com.sanathcoding.noteapplication.feature_note.domain.use_case.NoteUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NoteAppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDatabase {
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            NOTE_DB_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteDao(db: NoteDatabase): NoteDao {
        return db.dao
    }

    @Provides
    @Singleton
    fun provideNoteNoteUses(repository: NoteRepository, app: Application): NoteUseCases {
        return NoteUseCases(
            getNotes = GetNotes(repository),
            deleteNote = DeleteNote(repository),
            addNote = AddNote(repository, app)
        )
    }

}