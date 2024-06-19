package com.compose.noted.ui.presentation.addEdit

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.noted.utils.Utils
import com.compose.noted.core.data.local.InvalidNoteException
import com.compose.noted.domain.model.Note
import com.compose.noted.domain.usecase.NoteUseCase
import com.compose.noted.ui.commons.AddEditNoteEvent
import com.compose.noted.ui.commons.NoteTextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditViewModel @Inject constructor(
    private val useCase: NoteUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _title = mutableStateOf(
        NoteTextFieldState(
            hint = "Title"
        )
    )
    val title: State<NoteTextFieldState> = _title

    private val _content = mutableStateOf(
        NoteTextFieldState(
            hint = "Content"
        )
    )
    val content: State<NoteTextFieldState> = _content

    private val _color = mutableIntStateOf(Utils.noteColors.first().toArgb())
    val color: State<Int> = _color

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentNoteId: Int? = null

    init {
        savedStateHandle.get<Int>("noteId")?.let { noteId ->
            if (noteId != -1) {
                viewModelScope.launch {
                    useCase.getNote(noteId)?.also {
                        currentNoteId = it.id
                        _title.value = title.value.copy(
                            text = it.title,
                            isHintVisible = false
                        )
                        _content.value = content.value.copy(
                            text = it.content,
                            isHintVisible = false
                        )
                    }
                }
            }
        }

    }

    fun onEvent(event: AddEditNoteEvent) {
        when (event) {
            is AddEditNoteEvent.EnteredTitle -> {
                _title.value = title.value.copy(
                    text = event.value
                )
            }

            is AddEditNoteEvent.IsFocusTitle -> {
                _title.value = title.value.copy(
                    isHintVisible = !event.focusState.isFocused && title.value.text.isBlank()
                )
            }

            is AddEditNoteEvent.EnteredContent -> {
                _content.value = content.value.copy(
                    text = event.value
                )
            }

            is AddEditNoteEvent.IsFocusContent -> {
                _content.value = content.value.copy(
                    isHintVisible = !event.focusState.isFocused && content.value.text.isBlank()
                )
            }

            is AddEditNoteEvent.ChangeColor -> {
                _color.intValue = event.color
            }

            AddEditNoteEvent.SaveNote -> {
                viewModelScope.launch {
                    try {
                        useCase.addNote(
                            Note(
                                title = title.value.text,
                                content = content.value.text,
                                timeStamp = System.currentTimeMillis(),
                                color = color.value,
                                id = currentNoteId
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveNote)
                    } catch (e: InvalidNoteException) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message ?: "Couldn't save note"
                            )
                        )
                    }
                }
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        data object SaveNote : UiEvent()
    }
}