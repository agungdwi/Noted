package com.compose.noted.domain.usecase

import com.compose.noted.core.data.local.InvalidNoteException
import com.compose.noted.domain.INoteRepository
import com.compose.noted.domain.model.Note

class AddNote(
    private val repository: INoteRepository
) {
    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {
        if(note.content.isBlank()){
            throw InvalidNoteException("The content of the note can't be empty.")
        }
        if(note.title.isBlank()){
            throw InvalidNoteException("The title of the note can't be empty.")
        }
        repository.insertNote(note)
    }

}