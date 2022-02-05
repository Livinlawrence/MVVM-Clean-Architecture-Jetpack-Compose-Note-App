package com.livin.mvvmcleanarchitecturejetpackcomposenoteapp.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.livin.mvvmcleanarchitecturejetpackcomposenoteapp.ui.theme.*

@Entity
data class Note(
    var title: String,
    var content: String,
    var timeStamp: Long,
    var color: Int,
    @PrimaryKey val id: Int? = null
) {
    companion object {
        val colors = listOf(RedOrange, LightGreen, RedPink, BabyBlue, Violet)
    }
}


class InvalidNoteException(message: String) : Exception(message)