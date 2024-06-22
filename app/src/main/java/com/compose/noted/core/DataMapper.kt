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

}