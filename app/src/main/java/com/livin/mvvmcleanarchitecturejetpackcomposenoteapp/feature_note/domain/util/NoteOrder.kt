package com.livin.mvvmcleanarchitecturejetpackcomposenoteapp.feature_note.domain.util

sealed class NoteOrder(val noteOrderType: NoteOrderType) {

    class Title(orderType: NoteOrderType) : NoteOrder(orderType)
    class Date(orderType: NoteOrderType) : NoteOrder(orderType)
    class Color(orderType: NoteOrderType) : NoteOrder(orderType)


    fun copy(orderType: NoteOrderType): NoteOrder {
        return when (this) {
            is Title -> Title(orderType)
            is Date -> Date(orderType)
            is Color -> Color(orderType)
        }
    }
}
