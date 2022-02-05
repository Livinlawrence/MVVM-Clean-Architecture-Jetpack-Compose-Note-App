package com.livin.mvvmcleanarchitecturejetpackcomposenoteapp.feature_note.presentation.note

import com.livin.mvvmcleanarchitecturejetpackcomposenoteapp.feature_note.domain.model.Note
import com.livin.mvvmcleanarchitecturejetpackcomposenoteapp.feature_note.domain.util.NoteOrder
import com.livin.mvvmcleanarchitecturejetpackcomposenoteapp.feature_note.domain.util.NoteOrderType

data class NoteState(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(NoteOrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)