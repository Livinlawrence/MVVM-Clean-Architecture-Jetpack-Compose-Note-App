package com.livin.mvvmcleanarchitecturejetpackcomposenoteapp.feature_note.presentation.add_edit

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.livin.mvvmcleanarchitecturejetpackcomposenoteapp.feature_note.domain.model.InvalidNoteException
import com.livin.mvvmcleanarchitecturejetpackcomposenoteapp.feature_note.domain.model.Note
import com.livin.mvvmcleanarchitecturejetpackcomposenoteapp.feature_note.domain.use_case.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddEditViewModel @Inject constructor(
    private val notesUseCase: NoteUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {



    private val _noteTitle = mutableStateOf(NoteTextFieldState(hint = "Enter title"))
    val noteTitle: State<NoteTextFieldState> = _noteTitle

    private val _noteContent = mutableStateOf(NoteTextFieldState(hint = "Add content"))
    val noteContent: State<NoteTextFieldState> = _noteContent


    private val _noteColor = mutableStateOf(Note.colors.random().toArgb())
    val noteColor: State<Int> = _noteColor

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentNoteId: Int? = null


    init {

        savedStateHandle.get<Int>("noteId")?.let { noteId ->
            if (noteId != -1) {
                viewModelScope.launch {

                    notesUseCase.getNoteUseCase(noteId)?.also { note ->
                        currentNoteId = note.id
                        _noteTitle.value = noteTitle.value.copy(
                            title = note.title,
                            hintShown = false
                        )
                        _noteContent.value = noteContent.value.copy(
                            title = note.content,
                            hintShown = false
                        )
                        _noteColor.value = note.color
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditNoteEvent) {
        when (event) {
            is AddEditNoteEvent.EnteredTitle -> {
                _noteTitle.value = noteTitle.value.copy(
                    title = event.value
                )
            }
            is AddEditNoteEvent.ChangeTitleFocus -> {
                _noteTitle.value = noteTitle.value.copy(
                    hintShown = !event.focusState.isFocused &&
                            noteTitle.value.title.isBlank()
                )
            }
            is AddEditNoteEvent.EnteredContent -> {
                _noteContent.value = _noteContent.value.copy(
                    title = event.value
                )
            }
            is AddEditNoteEvent.ChangeContentFocus -> {
                _noteContent.value = _noteContent.value.copy(
                    hintShown = !event.focusState.isFocused &&
                            _noteContent.value.title.isBlank()
                )
            }
            is AddEditNoteEvent.ChangeColor -> {
                _noteColor.value = event.color
            }
            is AddEditNoteEvent.SaveNote -> {
                viewModelScope.launch(Dispatchers.IO) {
                    try {
                        notesUseCase.addNoteUseCase(
                            Note(
                                title = noteTitle.value.title,
                                content = noteContent.value.title,
                                timeStamp = System.currentTimeMillis(),
                                color = noteColor.value,
                                id = currentNoteId
                            )
                        )
                        _eventFlow.emit(UIEvent.SaveNote)
                    } catch (e: InvalidNoteException) {
                        _eventFlow.emit(
                            UIEvent.ShowSnackBar(
                                message = e.message ?: "Couldn't save note"
                            )
                        )
                    }
                }
            }
        }
    }

    sealed class UIEvent {
        data class ShowSnackBar(val message: String) : UIEvent()
        object SaveNote : UIEvent()
    }
}
