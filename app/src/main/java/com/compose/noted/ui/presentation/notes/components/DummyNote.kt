package com.compose.noted.ui.presentation.notes.components

import androidx.compose.ui.graphics.toArgb
import com.compose.noted.domain.model.Note
import com.compose.noted.domain.utils.NoteOrder
import com.compose.noted.domain.utils.OrderType
import com.compose.noted.ui.commons.NoteState
import com.compose.noted.ui.theme.RetroBlue

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

    val sampleEmpty = emptyList<Note>()

    // Creating a dummy NoteState
    val dummyNoteState = NoteState(
        notes = sampleEmpty,
        noteOrder = NoteOrder.Date(OrderType.Descending),
        isOrderSectionVisible = true
    )
}