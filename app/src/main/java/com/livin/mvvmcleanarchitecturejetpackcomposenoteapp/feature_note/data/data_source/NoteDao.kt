package com.livin.mvvmcleanarchitecturejetpackcomposenoteapp.feature_note.data.data_source

import androidx.room.*
import com.livin.mvvmcleanarchitecturejetpackcomposenoteapp.feature_note.domain.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM note")
    fun getAllNotes(): Flow<List<Note>>

    @Query("SELECT * FROM note WHERE id =:id ")
    suspend fun getNoteById(id:Int):Note?

    @Delete
    suspend fun deleteNote(note: Note)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(note: Note)
}