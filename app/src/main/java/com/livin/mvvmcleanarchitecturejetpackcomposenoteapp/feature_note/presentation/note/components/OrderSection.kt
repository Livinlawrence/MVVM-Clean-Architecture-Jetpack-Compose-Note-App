package com.livin.mvvmcleanarchitecturejetpackcomposenoteapp.feature_note.presentation.note.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.livin.mvvmcleanarchitecturejetpackcomposenoteapp.R
import com.livin.mvvmcleanarchitecturejetpackcomposenoteapp.feature_note.domain.util.NoteOrder
import com.livin.mvvmcleanarchitecturejetpackcomposenoteapp.feature_note.domain.util.NoteOrderType

@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    noteOrder: NoteOrder = NoteOrder.Date(NoteOrderType.Descending),
    orderChange: (NoteOrder) -> Unit
) {

    Column(
        modifier = modifier
    ) {

        Row(modifier = Modifier.fillMaxWidth()) {
            DefaultRadioButton(
                text = stringResource(id = R.string.title),
                selected = noteOrder is NoteOrder.Title,
                onCheck = {
                    orderChange(NoteOrder.Title(noteOrder.noteOrderType))
                })

            Spacer(modifier = Modifier.width(10.dp))

            DefaultRadioButton(
                text = stringResource(id = R.string.date),
                selected = noteOrder is NoteOrder.Date,
                onCheck = {
                    orderChange(NoteOrder.Date(noteOrder.noteOrderType))
                })

            Spacer(modifier = Modifier.width(10.dp))

            DefaultRadioButton(
                text = stringResource(id = R.string.color),
                selected = noteOrder is NoteOrder.Color,
                onCheck = {
                    orderChange(NoteOrder.Color(noteOrder.noteOrderType))
                })
        }

        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = "Ascending",
                selected = noteOrder.noteOrderType is NoteOrderType.Ascending,
                onCheck = {
                    orderChange(noteOrder.copy(NoteOrderType.Ascending))
                })

            Spacer(modifier = Modifier.width(10.dp))

            DefaultRadioButton(
                text = "Descending",
                selected = noteOrder.noteOrderType is NoteOrderType.Descending,
                onCheck = {
                    orderChange(noteOrder.copy(NoteOrderType.Descending))
                })
        }
    }
}