package com.livin.mvvmcleanarchitecturejetpackcomposenoteapp.feature_note.presentation.note

import com.livin.mvvmcleanarchitecturejetpackcomposenoteapp.feature_note.domain.model.Note
import com.livin.mvvmcleanarchitecturejetpackcomposenoteapp.feature_note.domain.util.NoteOrder

sealed class NoteEvent {

    data class Order(val noteOrder:NoteOrder):NoteEvent()
    data class Delete(val note:Note):NoteEvent()
    object RestoreNote:NoteEvent()
    object ToggleNote:NoteEvent()
}