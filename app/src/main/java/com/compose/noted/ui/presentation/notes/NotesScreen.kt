package com.compose.noted.ui.presentation.notes

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.compose.noted.domain.model.Note
import com.compose.noted.domain.utils.NoteOrder
import com.compose.noted.ui.commons.NoteEvent
import com.compose.noted.ui.commons.NoteState
import com.compose.noted.ui.presentation.notes.components.DummyNote
import com.compose.noted.ui.presentation.notes.components.NoteItem
import com.compose.noted.ui.presentation.notes.components.OrderRadio
import com.compose.noted.ui.theme.NotedTheme
import kotlinx.coroutines.launch


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun NoteScreen(
    viewModel: NotesViewModel = hiltViewModel(),
    navigateToAdd: () -> Unit,
    navigateToEdit: (Int, Int) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
) {

    val state = viewModel.state.collectAsState().value
    val query = viewModel.query.value

    NoteContent(state = state,
        query = query,
        onOrderChange = { order -> viewModel.onEvent(NoteEvent.Order(order)) },
        onToggleOrderSectionClick = { viewModel.onEvent(NoteEvent.ToggleOrderSection) },
        onDeleteClick = { note -> viewModel.onEvent(NoteEvent.DeleteNote(note)) },
        onUndoClick = { viewModel.onEvent(NoteEvent.RestoreNote) },
        navigateToAdd = navigateToAdd,
        navigateToEdit = { id, color -> navigateToEdit(id, color) },
        onQueryChange = { newQuery -> viewModel.onEvent(NoteEvent.SearchNote(newQuery)) },
        sharedTransitionScope = sharedTransitionScope,
        animatedVisibilityScope = animatedVisibilityScope)

}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun NoteContent(
    state: NoteState,
    query: String,
    onOrderChange: (NoteOrder) -> Unit,
    onToggleOrderSectionClick: () -> Unit,
    onDeleteClick: (Note) -> Unit,
    onUndoClick: () -> Unit,
    navigateToAdd: () -> Unit,
    navigateToEdit: (Int, Int) -> Unit,
    onQueryChange: (String) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val staggeredGridState = rememberLazyStaggeredGridState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToAdd, containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Note")
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            LazyVerticalStaggeredGrid(
                state = staggeredGridState,
                columns = StaggeredGridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalItemSpacing = 4.dp
            ) {
                item(span = StaggeredGridItemSpan.FullLine) {
                    SearchBar(
                        query = query,
                        onQueryChange = onQueryChange,
                        onToggleOrderSectionClick = onToggleOrderSectionClick
                    )
                }

                item(span = StaggeredGridItemSpan.FullLine) {
                    AnimatedVisibility(
                        visible = state.isOrderSectionVisible,
                        enter = fadeIn() + slideInVertically(),
                        exit = fadeOut() + slideOutVertically()
                    ) {
                        OrderRadio(modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp, horizontal = 8.dp),
                            noteOrder = state.noteOrder,
                            onOrderChange = { order ->
                                onOrderChange(order)
                            })
                    }
                }

                items(state.notes, key = { it.id ?: 0 }) { note ->
                    NoteItem(
                        note = note,
                        modifier = Modifier
                            .padding(4.dp)
                            .fillMaxWidth()
                            .animateItem(
                                placementSpec = tween(
                                    durationMillis = 300, delayMillis = 0
                                )
                            ), // This line adds the placement animation
                        onDeleteClick = {
                            onDeleteClick(note)
                            scope.launch {
                                val result = snackbarHostState.showSnackbar(
                                    message = "Note deleted", actionLabel = "Undo"
                                )
                                if (result == SnackbarResult.ActionPerformed) {
                                    onUndoClick()
                                }
                            }
                        },
                        onClick = {
                            note.id?.let { it1 -> navigateToEdit(it1, note.color) }

                        },
                        sharedTransitionScope = sharedTransitionScope,
                        animatedVisibilityScope = animatedVisibilityScope
                        )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onToggleOrderSectionClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    SearchBar(query = query,
        onQueryChange = onQueryChange,
        onSearch = {},
        active = false,
        onActiveChange = {},
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        trailingIcon = {
            IconButton(
                onClick = onToggleOrderSectionClick,
            ) {
                Icon(imageVector = Icons.Default.Sort, contentDescription = "Sort")
            }
        },

        placeholder = {
            Text("Search Notes")
        },
        shape = MaterialTheme.shapes.large,
        modifier = modifier.fillMaxWidth()
    ) {}
}

/*@Preview(showBackground = true)
@Composable
fun NoteContentPreview() {
    NotedTheme {

        NoteContent(state = DummyNote.dummyNoteState,
            onOrderChange = {},
            query = "",
            onToggleOrderSectionClick = { *//*TODO*//* },
            onDeleteClick = {},
            onUndoClick = { *//*TODO*//* },
            navigateToAdd = { *//*TODO*//* },
            navigateToEdit = { _, _ -> *//*TODO*//* },
            onQueryChange = { })
    }
}*/





