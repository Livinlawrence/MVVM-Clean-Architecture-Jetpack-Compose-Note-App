package com.livin.mvvmcleanarchitecturejetpackcomposenoteapp.feature_note.domain.use_case

class NoteUseCases(
    val deleteNoteUseCase: DeleteNoteUseCase,
    val getNotesUseCase: GetNotesUseCase,
    val addNoteUseCase: AddNoteUseCase,
    val getNoteUseCase: GetNoteUseCase
)