package com.livin.mvvmcleanarchitecturejetpackcomposenoteapp.di

import android.app.Application
import androidx.room.Room
import com.livin.mvvmcleanarchitecturejetpackcomposenoteapp.feature_note.data.data_source.AppDataBase
import com.livin.mvvmcleanarchitecturejetpackcomposenoteapp.feature_note.data.data_source.AppDataBase.Companion.DATABASE_NAME
import com.livin.mvvmcleanarchitecturejetpackcomposenoteapp.feature_note.data.repository.NoteRepoImpl
import com.livin.mvvmcleanarchitecturejetpackcomposenoteapp.feature_note.domain.repository.NoteRepository
import com.livin.mvvmcleanarchitecturejetpackcomposenoteapp.feature_note.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): AppDataBase {
        return Room.databaseBuilder(app, AppDataBase::class.java, DATABASE_NAME).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(dataBase: AppDataBase): NoteRepository {
        return NoteRepoImpl(dataBase.noteDao)
    }

    @Provides
    @Singleton
    fun provideUseCase(noteRepository: NoteRepository): NoteUseCases {

        return NoteUseCases(
            DeleteNoteUseCase(noteRepository),
            GetNotesUseCase(noteRepository),
            AddNoteUseCase(noteRepository),
            GetNoteUseCase(noteRepository)
        )
    }
}