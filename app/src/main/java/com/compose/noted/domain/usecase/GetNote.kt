package com.compose.noted.domain.usecase

import com.compose.noted.domain.INoteRepository
import com.compose.noted.domain.model.Note

class GetNote (
    private val repository: INoteRepository
){

    suspend operator fun invoke(id: Int): Note? {
        return repository.getNoteById(id)
    }
}