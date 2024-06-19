package com.compose.noted.utils

sealed class Screen(val route:String) {
    data object NotesScree: Screen("notes_screen")
    data object AddEditNoteScreen: Screen("add_edit_note_screen")
}