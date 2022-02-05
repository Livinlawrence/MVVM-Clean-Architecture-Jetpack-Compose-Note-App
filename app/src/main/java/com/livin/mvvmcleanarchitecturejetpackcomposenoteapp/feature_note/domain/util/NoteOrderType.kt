package com.livin.mvvmcleanarchitecturejetpackcomposenoteapp.feature_note.domain.util

sealed class NoteOrderType(){
    object Ascending:NoteOrderType()
    object Descending:NoteOrderType()
}
