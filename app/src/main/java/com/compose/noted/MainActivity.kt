package com.compose.noted

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.compose.noted.ui.presentation.addEdit.AddEditNoteScreen
import com.compose.noted.ui.presentation.notes.NoteScreen
import com.compose.noted.ui.presentation.notes.NotesViewModel
import com.compose.noted.ui.theme.NotedTheme
import com.compose.noted.utils.Screen
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalSharedTransitionApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: SplashViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition{
                viewModel.isLoading.value
            }

        }
        enableEdgeToEdge()
        setContent {
            NotedTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    SharedTransitionLayout {
                        val navController = rememberNavController()
                        NavHost(
                            navController = navController,
                            startDestination = Screen.NotesScree.route
                        ) {
                            composable(route = Screen.NotesScree.route){
                                NoteScreen(
                                    navigateToAdd = {
                                        navController.navigate(Screen.AddEditNoteScreen.route)
                                    },
                                    navigateToEdit = { id, color ->
                                        navController.navigate(Screen.AddEditNoteScreen.createRoute(id, color))
                                    },
                                    sharedTransitionScope = this@SharedTransitionLayout,
                                    animatedVisibilityScope = this
                                )
                            }
                            composable(route = Screen.AddEditNoteScreen.route,
                                arguments = listOf(
                                    navArgument(
                                        name = "noteId"
                                    ){
                                        type = NavType.IntType
                                        defaultValue = -1
                                    },
                                    navArgument(
                                        name = "noteColor"
                                    ) {
                                        type = NavType.IntType
                                        defaultValue = -1
                                    }
                                )

                            ){
                                val color = it.arguments?.getInt("noteColor") ?: -1
                                val noteId = it.arguments?.getInt("noteId") ?: -1
                                AddEditNoteScreen(
                                    navController = navController,
                                    noteColor = color,
                                    noteId = noteId,
                                    sharedTransitionScope = this@SharedTransitionLayout,
                                    animatedVisibilityScope = this
                                )
                            }



                        }

                    }


                }
            }
        }
    }
}
