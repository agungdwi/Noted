package com.compose.noted.utils

import com.compose.noted.ui.theme.RetroBlue
import com.compose.noted.ui.theme.RetroGreen
import com.compose.noted.ui.theme.RetroOrange
import com.compose.noted.ui.theme.RetroRed
import com.compose.noted.ui.theme.RetroYellow

object Utils {

    val noteColors = listOf(RetroRed, RetroBlue, RetroGreen, RetroOrange, RetroYellow)

    sealed class Screen(val route:String) {
        data object NotesScree: Screen("notes_screen")
        data object AddEditNoteScreen: Screen("add_edit_note_screen")
    }
}