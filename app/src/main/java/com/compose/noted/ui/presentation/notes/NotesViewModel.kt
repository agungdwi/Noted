package com.compose.noted.ui.presentation.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.compose.noted.domain.model.Note
import com.compose.noted.domain.usecase.NoteUseCase
import com.compose.noted.domain.utils.NoteOrder
import com.compose.noted.domain.utils.OrderType
import com.compose.noted.ui.commons.NoteEvent
import com.compose.noted.ui.commons.NoteState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(private val useCase: NoteUseCase) : ViewModel() {

    private val _state = MutableStateFlow(NoteState())
    val state: StateFlow<NoteState> = _state

    private var recentlyDeletedNote: Note? = null

    private var getNotesJob: Job? = null

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    init {
        getNotes(NoteOrder.Date(OrderType.Descending))
    }

    fun onEvent(event: NoteEvent) {
        when (event) {
            is NoteEvent.Order -> {
                if (state.value.noteOrder::class == event.noteOrder::class &&
                    state.value.noteOrder.orderType == event.noteOrder.orderType) {
                    return
                }
                getNotes(event.noteOrder,
                    //isOrderChange = true
                )
            }

            is NoteEvent.DeleteNote -> {
                viewModelScope.launch {
                    useCase.deleteNote(event.note)
                    recentlyDeletedNote = event.note
           //         refreshNotes()
                }
            }

            is NoteEvent.RestoreNote -> {
                viewModelScope.launch {
                    useCase.addNote(recentlyDeletedNote ?: return@launch)
                    recentlyDeletedNote = null
            //        refreshNotes()
                }
            }

            is NoteEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }

            is NoteEvent.SearchNote -> {
                viewModelScope.launch(Dispatchers.Default) {
                    _query.value = event.query
                    delay(500L) // debounce delay
                    getNotes(state.value.noteOrder)
                }
            }
        }
    }

    private fun getNotes(noteOrder: NoteOrder,
                         //isOrderChange: Boolean = false
    ) {
        getNotesJob?.cancel()
        getNotesJob = useCase.getNotes(noteOrder)
            .onEach { notes ->
                val filteredNotes = notes.filter {
                    it.title.contains(_query.value, ignoreCase = true) || it.content.contains(_query.value, ignoreCase = true)
                }
                _state.update {
                    it.copy(
                        notes = filteredNotes,
                        noteOrder = noteOrder
                    )
                }
//                _state.value = state.value.copy(
//                    notes = filteredNotes,
//                    noteOrder = noteOrder
//                )
//                if (isOrderChange && _query.value.isNotEmpty()) {
//                    // Apply filter immediately on order change if a search query is active
//                    val filteredNotes = notes.filter {
//                        it.title.contains(_query.value, ignoreCase = true) || it.content.contains(_query.value, ignoreCase = true)
//                    }
//                    _state.value = state.value.copy(
//                        notes = filteredNotes,
//                        noteOrder = noteOrder
//                    )
//                } else {
//                    _state.value = state.value.copy(
//                        notes = notes,
//                        noteOrder = noteOrder
//                    )
//                    applyQueryFilter()
//                }
            }
            .launchIn(viewModelScope)
    }

//    private suspend fun searchNotes(newQuery: String) {
//        _query.value = newQuery
//        delay(500L) // debounce delay
//        getNotes(state.value.noteOrder)
////        applyQueryFilter()
//    }

//    private suspend fun applyQueryFilter() {
//        val currentQuery = _query.value
//        if (currentQuery.isEmpty()) {
//            getNotes(_state.value.noteOrder)
//            return
//        }
//
//        getNotesJob?.cancel()
//        getNotesJob = viewModelScope.launch {
//            useCase.getNotes(state.value.noteOrder)
//                .collect { notes ->
//                    val filteredNotes = notes.filter {
//                        it.title.contains(currentQuery, ignoreCase = true) || it.content.contains(currentQuery, ignoreCase = true)
//                    }
//                    _state.value = state.value.copy(
//                        notes = filteredNotes,
//                    )
//                }
//        }
//    }

//    private fun refreshNotes() {
//        viewModelScope.launch {
//            getNotes(_state.value.noteOrder)
//            if (currentQuery.isEmpty()) {
//                getNotes(_state.value.noteOrder)
//            } else {
//                applyQueryFilter(_state.value.noteOrder)
//            }
//        }
//    }
}