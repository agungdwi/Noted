package com.compose.noted.ui.presentation.notes.components

import androidx.compose.ui.graphics.toArgb
import com.compose.noted.domain.model.Note
import com.compose.noted.domain.utils.NoteOrder
import com.compose.noted.domain.utils.OrderType
import com.compose.noted.ui.commons.NoteState
import com.compose.noted.ui.theme.RetroBlue
import com.compose.noted.ui.theme.RetroRed
import com.compose.noted.ui.theme.RetroYellow

object DummyNote {
    val dummyNote = Note(
        id = 1,
        title = "Title",
        content = "Content asdadas asdasd adasdas asdadsa sadasd a adasdas asdsadas dadasd as asd asd asd ad asdasda" +
                "asdasdadasdadasdasdasdasd" +
                "sadasdasdasdasda" +
                "dsdasdasdasdasdas" +
                "sdasdasdasd" +
                "asdasdasdasdasdasdasdsadas" +
                "sadasdasdasdasdasddasdasd asdasdasdas asdasdasda sadasdasdasd" +
                "adsadasdasdasdasdasdsadslfoifhisodhfsobfsObOPDHSoSHDadakLDSaasdasdsadascs" +
                "asdasdasdasdasdas" +
                "fasfasdsa" +
                "/n" +
                "/n" +
                "dasdasdsdsadsadsadsadsadsadsadasd",
        color = RetroBlue.toArgb(),
        timeStamp = 20240611
    )

    // Sample data for Note
    val sampleNotes = listOf(
        Note(
            id = 1,
            title = "Sample Note 1",
            content = "This is the content of sample note 1.",
            color = RetroBlue.toArgb(), // Orange color
            timeStamp = System.currentTimeMillis()
        ),
        Note(
            id = 2,
            title = "Sample Note 2",
            content = "This is the content of sample note 2.",
            color = RetroYellow.toArgb(), // Green color
            timeStamp = System.currentTimeMillis() - 100000L
        ),
        Note(
            id = 3,
            title = "Sample Note 3",
            content = "This is the content of sample note 3.",
            color = RetroRed.toArgb(), // Red color
            timeStamp = System.currentTimeMillis() - 200000L
        )
    )

    val sampleEmpty = emptyList<Note>()

    // Creating a dummy NoteState
    val dummyNoteState = NoteState(
        notes = sampleEmpty,
        noteOrder = NoteOrder.Date(OrderType.Descending),
        isOrderSectionVisible = true
    )
}