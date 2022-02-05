package com.livin.mvvmcleanarchitecturejetpackcomposenoteapp.feature_note.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.livin.mvvmcleanarchitecturejetpackcomposenoteapp.feature_note.domain.model.Note

@Database(
    entities = [Note::class], version = 1
)
abstract class AppDataBase : RoomDatabase() {

    abstract val noteDao: NoteDao

    companion object{
        val DATABASE_NAME = "demo"
    }
}