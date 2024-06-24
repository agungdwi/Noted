package com.compose.noted.ui.commons

import com.compose.noted.domain.model.Note
import com.compose.noted.domain.utils.NoteOrder
import com.compose.noted.domain.utils.OrderType

data class NoteState (
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false,
    val isSearchActive : Boolean = false
)