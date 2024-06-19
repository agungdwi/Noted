package com.compose.noted.ui.presentation.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.noted.domain.model.Note
import com.compose.noted.domain.usecase.NoteUseCase
import com.compose.noted.domain.utils.NoteOrder
import com.compose.noted.domain.utils.OrderType
import com.compose.noted.ui.commons.NoteEvent
import com.compose.noted.ui.commons.NoteState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(private val useCase: NoteUseCase) : ViewModel (){

    private val _state = MutableStateFlow(NoteState())
    val state : StateFlow<NoteState> = _state

    private var recentlyDeletedNote: Note? = null

    private val getNotesJob : Job? = null

    init {
        getNotes(NoteOrder.Date(OrderType.Descending))
    }
    fun onEvent(event: NoteEvent){
        when(event){
            is NoteEvent.Order -> {
                if(state.value.noteOrder::class == event.noteOrder::class
                    &&
                    state.value.noteOrder.orderType == event.noteOrder.orderType
                    ){
                    return
                }
                getNotes(event.noteOrder)
            }
            is NoteEvent.DeleteNote -> {
                viewModelScope.launch {
                    useCase.deleteNote(event.note)
                    recentlyDeletedNote = event.note

                }

            }
            is NoteEvent.RestoreNote -> {
                viewModelScope.launch {
                    useCase.addNote(recentlyDeletedNote ?: return@launch)
                    recentlyDeletedNote = null
                }
            }
            is NoteEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
        }
    }

    private fun getNotes(noteOrder: NoteOrder){
        getNotesJob?.cancel()
        useCase.getNotes(noteOrder).onEach { notes ->
            _state.value = state.value.copy(
                notes = notes,
                noteOrder = noteOrder
            )
        }.launchIn(viewModelScope)
    }


}