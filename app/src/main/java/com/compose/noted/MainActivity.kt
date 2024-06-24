package com.compose.noted

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.compose.noted.ui.presentation.addEdit.AddEditNoteScreen
import com.compose.noted.ui.presentation.notes.NoteScreen
import com.compose.noted.ui.theme.NotedTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: SplashViewModel by viewModels()
    @OptIn(ExperimentalSharedTransitionApi::class)
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
                SharedTransitionLayout {

                    Surface(
                        color = MaterialTheme.colorScheme.background
                    ) {
                        val navController = rememberNavController()
                        NavHost(
                            navController = navController,
                            startDestination = ScreenHome
                        ) {
                            composable<ScreenHome>{
                                NoteScreen(
                                    navigateToAddEdit = { id, color ->
                                        navController.navigate(ScreenAddEdit(noteId = id, color = color))
                                    },
                                    animatedVisibilityScope = this,
                                    sharedTransitionScope = this@SharedTransitionLayout
                                )
                            }
                            composable<ScreenAddEdit>{
                                val args = it.toRoute<ScreenAddEdit>()
                                val color = args.color
                                val id= args.noteId
                                AddEditNoteScreen(
                                    navController = navController,
                                    noteColor = color,
                                    noteId = id,
                                    animatedVisibilityScope = this,
                                    sharedTransitionScope = this@SharedTransitionLayout
                                )
                            }



                        }


                    }
                }
            }
        }
    }
}

@Serializable
object ScreenHome

@Serializable
data class ScreenAddEdit(
    val noteId:Int,
    val color:Int
)
