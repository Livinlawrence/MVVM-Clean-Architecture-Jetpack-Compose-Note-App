package com.livin.mvvmcleanarchitecturejetpackcomposenoteapp.feature_note.data.repository

import com.livin.mvvmcleanarchitecturejetpackcomposenoteapp.feature_note.data.data_source.NoteDao
import com.livin.mvvmcleanarchitecturejetpackcomposenoteapp.feature_note.domain.model.Note
import com.livin.mvvmcleanarchitecturejetpackcomposenoteapp.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepoImpl(val noteDao: NoteDao) : NoteRepository {

    override fun getAllNotes(): Flow<List<Note>> {
        return noteDao.getAllNotes()
    }

    override suspend fun insertNote(note: Note) {
        noteDao.insertNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        noteDao.deleteNote(note)
    }

    override suspend fun getNoteById(id: Int): Note? {
        return noteDao.getNoteById(id)
    }
}