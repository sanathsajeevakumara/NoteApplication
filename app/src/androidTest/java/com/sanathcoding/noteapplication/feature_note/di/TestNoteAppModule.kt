package com.sanathcoding.noteapplication.feature_note.di

import android.app.Application
import androidx.room.Room
import com.sanathcoding.noteapplication.feature_note.data.data_source.NoteDao
import com.sanathcoding.noteapplication.feature_note.data.data_source.NoteDatabase
import com.sanathcoding.noteapplication.feature_note.domain.repository.NoteRepository
import com.sanathcoding.noteapplication.feature_note.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestNoteAppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDatabase {
        return Room.inMemoryDatabaseBuilder(
            app,
            NoteDatabase::class.java,
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
            addNote = AddNote(repository, app),
            getNote = GetNote(repository)
        )
    }

}