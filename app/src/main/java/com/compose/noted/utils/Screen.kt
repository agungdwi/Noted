package com.compose.noted.utils

sealed class Screen(val route:String) {
    data object NotesScree: Screen("notes_screen")
    data object AddEditNoteScreen: Screen("add_edit_note_screen?noteId={noteId}&noteColor={noteColor}"){
        fun createRoute(id:Int?, color: Int?) = "add_edit_note_screen?noteId=${id}&noteColor=${color}"
    }
}