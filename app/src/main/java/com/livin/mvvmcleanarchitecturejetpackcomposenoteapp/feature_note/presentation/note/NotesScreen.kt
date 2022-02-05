package com.livin.mvvmcleanarchitecturejetpackcomposenoteapp.feature_note.presentation.note

import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Face
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.livin.mvvmcleanarchitecturejetpackcomposenoteapp.feature_note.presentation.note.components.NoteItem
import com.livin.mvvmcleanarchitecturejetpackcomposenoteapp.feature_note.presentation.note.components.OrderSection
import com.livin.mvvmcleanarchitecturejetpackcomposenoteapp.feature_note.presentation.util.Screen
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@Composable
fun NotesScreen(
    navController: NavController,
    viewModel: NotesViewModel
) {

    val state = viewModel.noteState.value
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.AddEditNoteScreen.route)
                },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Note")
            }
        }, scaffoldState = scaffoldState
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Your Notes", style = MaterialTheme.typography.h5)
                IconButton(onClick = {
                    viewModel.onEvent(NoteEvent.ToggleNote)
                }) {
                    Icon(imageVector = Icons.Default.Face, contentDescription = "Sort")
                }
            }

            AnimatedVisibility(
                visible = state.isOrderSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                OrderSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    noteOrder = state.noteOrder,
                    orderChange = {
                        viewModel.onEvent(NoteEvent.Order(it))
                    }
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.notes) { note ->
                    NoteItem(
                        note = note,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate(
                                    Screen.AddEditNoteScreen.route +
                                            "?noteId=${note.id}&noteColor=${note.color}"
                                )
                            }, onDelete = {
                            viewModel.onEvent(NoteEvent.Delete(note))
                            scope.launch {
                                val result = scaffoldState.snackbarHostState.showSnackbar(
                                    "Deleted",
                                    actionLabel = "undo"
                                )
                                if (result == SnackbarResult.ActionPerformed) {
                                    viewModel.onEvent(NoteEvent.RestoreNote)
                                }
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }
}