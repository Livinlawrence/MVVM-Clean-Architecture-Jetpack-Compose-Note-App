package com.livin.mvvmcleanarchitecturejetpackcomposenoteapp.feature_note.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.livin.mvvmcleanarchitecturejetpackcomposenoteapp.feature_note.presentation.add_edit.AddEditNoteScreen
import com.livin.mvvmcleanarchitecturejetpackcomposenoteapp.feature_note.presentation.add_edit.AddEditViewModel
import com.livin.mvvmcleanarchitecturejetpackcomposenoteapp.feature_note.presentation.note.NotesScreen
import com.livin.mvvmcleanarchitecturejetpackcomposenoteapp.feature_note.presentation.note.NotesViewModel
import com.livin.mvvmcleanarchitecturejetpackcomposenoteapp.feature_note.presentation.ui.theme.MVVMCleanArchitectureJetpackComposeNoteAppTheme
import com.livin.mvvmcleanarchitecturejetpackcomposenoteapp.feature_note.presentation.util.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LaunchActivity : ComponentActivity() {
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MVVMCleanArchitectureJetpackComposeNoteAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.NotesScreen.route
                    ) {
                        val notesViewMode by viewModels<NotesViewModel>()
                        composable(route = Screen.NotesScreen.route) {
                            NotesScreen(
                                navController = navController,
                                viewModel = notesViewMode
                            )
                        }
                        val addEditNoteViewMode by viewModels<AddEditViewModel>()
                        composable(
                            route = Screen.AddEditNoteScreen.route +
                                    "?noteId={noteId}&noteColor={noteColor}",
                            arguments = listOf(
                                navArgument(
                                    name = "noteId"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                                navArgument(
                                    name = "noteColor"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                            )
                        ) {
                            val color = it.arguments?.getInt("noteColor") ?: -1
                            AddEditNoteScreen(
                                navController = navController,
                                noteColor = color,
                                viewModel = addEditNoteViewMode
                            )
                        }
                    }
                }
            }
        }
    }}
