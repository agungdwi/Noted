package com.compose.noted.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.compose.noted.ui.theme.RetroBlue
import com.compose.noted.ui.theme.RetroGreen
import com.compose.noted.ui.theme.RetroOrange
import com.compose.noted.ui.theme.RetroRed
import com.compose.noted.ui.theme.RetroYellow

@Entity
data class NoteEntity(
    @PrimaryKey
    val id : Int? = null,
    val title : String,
    val content : String,
    val color : Int,
) {
    companion object{
        val noteColors = listOf(RetroRed, RetroBlue, RetroGreen, RetroOrange, RetroYellow)
    }
}
