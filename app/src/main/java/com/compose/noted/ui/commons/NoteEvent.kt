package com.compose.noted.ui.commons

import com.compose.noted.domain.model.Note
import com.compose.noted.domain.utils.NoteOrder

sealed class NoteEvent {
    data class Order(val noteOrder: NoteOrder): NoteEvent()
    data class DeleteNote(val note: Note): NoteEvent()
    data object RestoreNote: NoteEvent()
    data object ToggleOrderSection: NoteEvent()
    data class SearchNote(val query: String): NoteEvent()
}