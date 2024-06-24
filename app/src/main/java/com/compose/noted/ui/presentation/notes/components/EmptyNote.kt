package com.compose.noted.ui.presentation.notes.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SpeakerNotesOff
import androidx.compose.material.icons.filled.StickyNote2
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.CreationExtras
import com.compose.noted.ui.theme.NotedTheme

@Composable
fun EmptyNote(
    modifier: Modifier = Modifier,
    isSearchActive: Boolean = true
){
    var text by remember {
        mutableStateOf("Make Your Activity Noted")
    }

    var icon by remember {
        mutableStateOf(Icons.Default.StickyNote2)
    }

    if(isSearchActive){
        text = "Note Not Found"
        icon = Icons.Default.SpeakerNotesOff
    }else{
        text = "Make Your Activity Noted"
        icon = Icons.Default.StickyNote2

    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.wrapContentWidth(Alignment.CenterHorizontally)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "Note Empty",
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = text, style = MaterialTheme.typography.titleLarge)
        }
    }


}

@Preview(showBackground = true)
@Composable
fun EmptyNotePreview(){
    NotedTheme {
        EmptyNote(isSearchActive = true)
    }
}