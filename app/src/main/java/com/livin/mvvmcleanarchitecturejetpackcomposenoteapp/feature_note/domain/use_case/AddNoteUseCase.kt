package com.livin.mvvmcleanarchitecturejetpackcomposenoteapp.feature_note.domain.use_case

import com.livin.mvvmcleanarchitecturejetpackcomposenoteapp.feature_note.domain.model.InvalidNoteException
import com.livin.mvvmcleanarchitecturejetpackcomposenoteapp.feature_note.domain.model.Note
import com.livin.mvvmcleanarchitecturejetpackcomposenoteapp.feature_note.domain.repository.NoteRepository
import kotlin.jvm.Throws

class AddNoteUseCase(val noteRepository: NoteRepository) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note:Note){

        if(note.title.isBlank()){
            throw InvalidNoteException("Title is empty")
        }

        if(note.content.isBlank()){
            throw InvalidNoteException("Content is empty")
        }

        noteRepository.insertNote(note)
    }
}