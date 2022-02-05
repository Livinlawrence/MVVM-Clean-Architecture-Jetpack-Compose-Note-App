package com.livin.mvvmcleanarchitecturejetpackcomposenoteapp.feature_note.presentation.note

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.livin.mvvmcleanarchitecturejetpackcomposenoteapp.feature_note.domain.model.Note
import com.livin.mvvmcleanarchitecturejetpackcomposenoteapp.feature_note.domain.use_case.NoteUseCases
import com.livin.mvvmcleanarchitecturejetpackcomposenoteapp.feature_note.domain.util.NoteOrder
import com.livin.mvvmcleanarchitecturejetpackcomposenoteapp.feature_note.domain.util.NoteOrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val notesUseCases: NoteUseCases
) : ViewModel() {

    private val _noteState = mutableStateOf(NoteState())
    val noteState: State<NoteState> = _noteState

    private var recentlyDeletedNote: Note? = null

    private var getNotesJob: Job? = null

    init {
        getNotes(NoteOrder.Date(NoteOrderType.Descending))
    }

    fun onEvent(event: NoteEvent) {
        when (event) {
            is NoteEvent.Order -> {
                if (
                    noteState.value.noteOrder::class == event.noteOrder::class &&
                    noteState.value.noteOrder.noteOrderType == event.noteOrder.noteOrderType
                ) {
                    return
                }

                getNotes(event.noteOrder)
            }
            is NoteEvent.Delete -> {
                viewModelScope.launch {
                    notesUseCases.deleteNoteUseCase.invoke(event.note)
                    recentlyDeletedNote = event.note
                }
            }
            NoteEvent.RestoreNote -> {
                viewModelScope.launch(Dispatchers.IO) {
                    recentlyDeletedNote?.let { notesUseCases.addNoteUseCase.invoke(it) }
                    recentlyDeletedNote = null
                }
            }
            NoteEvent.ToggleNote -> {
                _noteState.value =
                    noteState.value.copy(isOrderSectionVisible = !noteState.value.isOrderSectionVisible)
            }
        }
    }

    private fun getNotes(noteOrder: NoteOrder) {
        getNotesJob?.cancel()
        getNotesJob = notesUseCases.getNotesUseCase(noteOrder)
            .onEach { notes ->
                _noteState.value = noteState.value.copy(
                    notes = notes,
                    noteOrder = noteOrder
                )
            }.launchIn(viewModelScope)
    }

}
