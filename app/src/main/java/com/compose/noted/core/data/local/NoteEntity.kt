package com.compose.noted.core.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NoteEntity(
    @PrimaryKey
    val id : Int? = null,
    val title : String,
    val content : String,
    val color : Int,
    val timeStamp : Long,
)

class InvalidNoteException(message : String) : Exception(message)
