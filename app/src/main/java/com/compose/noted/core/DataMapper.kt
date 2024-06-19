package com.compose.noted.core

import com.compose.noted.core.data.local.NoteEntity
import com.compose.noted.domain.model.Note

object DataMapper {

    fun mapEntityToModel(entity: NoteEntity) = Note (
        id = entity.id,
        title = entity.title,
        content = entity.content,
        color = entity.color,
        timeStamp = entity.timeStamp,
    )

    fun mapModelToEntity(model : Note) = NoteEntity (
        id = model.id,
        title = model.title,
        content = model.content,
        color = model.color,
        timeStamp = model.timeStamp,
    )

//    fun mapModelToEntities(model: List<Note>): List<NoteEntity> {
//        val notes = ArrayList<NoteEntity>()
//        model.map {
//            val note = NoteEntity(
//                id = it.id,
//                title = it.title,
//                content = it.content,
//                color = it.color,
//                timeStamp = it.timeStamp,
//                )
//            notes.add(note)
//        }
//        return notes
//    }
}