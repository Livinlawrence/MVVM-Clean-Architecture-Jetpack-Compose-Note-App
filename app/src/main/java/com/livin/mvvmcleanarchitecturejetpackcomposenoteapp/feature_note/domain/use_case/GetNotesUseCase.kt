package com.livin.mvvmcleanarchitecturejetpackcomposenoteapp.feature_note.domain.use_case

import com.livin.mvvmcleanarchitecturejetpackcomposenoteapp.feature_note.domain.model.Note
import com.livin.mvvmcleanarchitecturejetpackcomposenoteapp.feature_note.domain.repository.NoteRepository
import com.livin.mvvmcleanarchitecturejetpackcomposenoteapp.feature_note.domain.util.NoteOrder
import com.livin.mvvmcleanarchitecturejetpackcomposenoteapp.feature_note.domain.util.NoteOrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetNotesUseCase(private val noteRepository: NoteRepository) {

    operator fun invoke(
        noteOrder: NoteOrder = NoteOrder.Date(NoteOrderType.Descending)
    ): Flow<List<Note>> {
        return noteRepository.getAllNotes().map { notes ->
            when (noteOrder.noteOrderType) {
                NoteOrderType.Ascending -> {
                    when (noteOrder) {
                        is NoteOrder.Title -> notes.sortedBy { it.title.toLowerCase() }
                        is NoteOrder.Date -> notes.sortedBy { it.timeStamp }
                        is NoteOrder.Color -> notes.sortedBy { it.color }
                    }
                }
                NoteOrderType.Descending -> {
                    when (noteOrder) {
                        is NoteOrder.Title -> notes.sortedByDescending { it.title.toLowerCase() }
                        is NoteOrder.Date -> notes.sortedByDescending { it.timeStamp }
                        is NoteOrder.Color -> notes.sortedByDescending { it.color }
                    }
                }
            }
        }
    }
}