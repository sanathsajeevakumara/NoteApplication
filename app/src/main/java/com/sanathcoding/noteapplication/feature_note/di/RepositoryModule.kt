package com.sanathcoding.noteapplication.feature_note.di

import com.sanathcoding.noteapplication.feature_note.data.repository.NoteRepositoryImpl
import com.sanathcoding.noteapplication.feature_note.domain.repository.NoteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindNoteRepository(noteRepositoryImpl: NoteRepositoryImpl): NoteRepository

}