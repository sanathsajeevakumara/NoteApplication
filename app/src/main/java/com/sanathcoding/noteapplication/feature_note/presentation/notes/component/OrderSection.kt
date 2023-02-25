package com.sanathcoding.noteapplication.feature_note.presentation.notes.component

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sanathcoding.noteapplication.R
import com.sanathcoding.noteapplication.feature_note.domain.util.NoteOrder
import com.sanathcoding.noteapplication.feature_note.domain.util.OrderType

@Composable
fun OrderSection(
    modifier: Modifier= Modifier,
    noteOrder: NoteOrder = NoteOrder.ByDate(OrderType.Descending),
    onOrderChange: (NoteOrder) -> Unit
) {
    Column(modifier = modifier) {
        Row(modifier = Modifier.fillMaxWidth()) {
            DefaultRadioButton(
                text = stringResource(R.string.orderby_title),
                isSelected = noteOrder is NoteOrder.ByTitle,
                onSelect = { onOrderChange(NoteOrder.ByTitle(noteOrder.orderType)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = stringResource(R.string.orderby_date),
                isSelected = noteOrder is NoteOrder.ByDate,
                onSelect = { onOrderChange(NoteOrder.ByDate(noteOrder.orderType)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = stringResource(R.string.orderby_color),
                isSelected = noteOrder is NoteOrder.ByColor,
                onSelect = { onOrderChange(NoteOrder.ByColor(noteOrder.orderType)) }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            DefaultRadioButton(
                text = stringResource(R.string.ascending),
                isSelected = noteOrder.orderType is OrderType.Ascending,
                onSelect = { onOrderChange(noteOrder.copy(OrderType.Ascending)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = stringResource(R.string.descending),
                isSelected = noteOrder.orderType is OrderType.Descending,
                onSelect = { onOrderChange(noteOrder.copy(OrderType.Descending)) }
            )
        }
    }
}