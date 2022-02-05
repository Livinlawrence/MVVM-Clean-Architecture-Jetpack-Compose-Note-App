package com.livin.mvvmcleanarchitecturejetpackcomposenoteapp.feature_note.presentation.add_edit

data class NoteTextFieldState(
    val title: String = "",
    val hint: String = "",
    val hintShown: Boolean = true
)