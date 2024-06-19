package com.compose.noted.domain.usecase

import com.compose.noted.domain.INoteRepository
import com.compose.noted.domain.model.Note

class DeleteNote(
    private val repository: INoteRepository
) {
    suspend operator fun invoke(note: Note) = repository.deleteNote(note)
}