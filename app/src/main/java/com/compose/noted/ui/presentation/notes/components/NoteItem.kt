package com.compose.noted.ui.presentation.notes.components

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.compose.noted.domain.model.Note
import com.compose.noted.ui.theme.NotedTheme

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun NoteItem(
    note: Note,
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 16.dp,
    onDeleteClick: () -> Unit,
    onClick: () -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
){
    with(sharedTransitionScope){
        Box(modifier = modifier
            .clip(RoundedCornerShape(cornerRadius))
            .background(Color(note.color))
            .clickable { onClick() }
            .sharedElement(
                state = rememberSharedContentState(key = "bg/${note.id}"),
                animatedVisibilityScope = animatedVisibilityScope,
                boundsTransform = {_,_ ->
                    tween(durationMillis = 500)
                }
            )){
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .padding(end = 32.dp)) {

                Text(
                    text = note.title,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .sharedElement(
                            state = rememberSharedContentState(key = "title/${note.id}"),
                            animatedVisibilityScope = animatedVisibilityScope,
                            boundsTransform = {_,_ ->
                                tween(durationMillis = 500)
                            }
                        )

                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = note.content,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 7,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .sharedElement(
                            state = rememberSharedContentState(key = "content/${note.id}"),
                            animatedVisibilityScope = animatedVisibilityScope,
                            boundsTransform = {_,_ ->
                                tween(durationMillis = 500)
                            }
                        )

                )

            }
            IconButton(
                onClick = onDeleteClick,
                modifier = Modifier.align(Alignment.BottomEnd)) {

                Icon(imageVector = Icons.Default.Delete , contentDescription ="Delete" )
            }


        }
    }


}

//@Preview(showBackground = true)
//@Composable
//fun NoteItemPreview(){
//    NotedTheme {
//        NoteItem(note = DummyNote.dummyNote,
//            onDeleteClick = {},
//            modifier = Modifier.padding(16.dp),
//            onClick = {},
//            sharedTransitionScope = remember {},
//            animatedVisibilityScope = remember {})
//    }
//}