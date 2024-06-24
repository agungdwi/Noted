package com.compose.noted.core.data

import com.compose.noted.core.DataMapper
import com.compose.noted.core.data.local.NoteDao
import com.compose.noted.domain.INoteRepository
import com.compose.noted.domain.model.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteRepository(
    private val dao: NoteDao
) : INoteRepository {
    override fun getNotes(): Flow<List<Note>> {
        return dao.getNotes().map { notes ->
            notes.map { noteEntity ->
                DataMapper.mapEntityToModel(noteEntity)
            }
        }
    }

    override suspend fun getNoteById(id: Int): Note? {
        return dao.getNoteById(id)?.let { DataMapper.mapEntityToModel(it) }
    }

    override suspend fun insertNote(note: Note) {
        val noteEntity = DataMapper.mapModelToEntity(note)
        return dao.insertNote(noteEntity)
    }

    override suspend fun deleteNote(note: Note) {
        val noteEntity = DataMapper.mapModelToEntity(note)
        return dao.deleteNote(noteEntity)
    }

}