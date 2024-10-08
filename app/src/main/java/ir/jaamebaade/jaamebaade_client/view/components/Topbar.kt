package ir.jaamebaade.jaamebaade_client.view.components

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.canopas.lib.showcase.IntroShowcaseScope
import com.canopas.lib.showcase.component.ShowcaseStyle
import ir.jaamebaade.jaamebaade_client.R
import ir.jaamebaade.jaamebaade_client.constants.AppRoutes
import ir.jaamebaade.jaamebaade_client.viewmodel.AudioViewModel
import ir.jaamebaade.jaamebaade_client.viewmodel.TopBarViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IntroShowcaseScope.TopBar(
    navController: NavController,
    viewModel: TopBarViewModel = hiltViewModel(),
    audioViewModel: AudioViewModel
) {
    val myIcon = painterResource(id = R.mipmap.logo)
    val backStackEntry by navController.currentBackStackEntryAsState()
    val canPop =
        (backStackEntry?.destination?.route != AppRoutes.DOWNLOADED_POETS_SCREEN.toString()
                && backStackEntry?.destination?.route != AppRoutes.SETTINGS_SCREEN.toString()
                && backStackEntry?.destination?.route != AppRoutes.SEARCH_SCREEN.toString()
                && backStackEntry?.destination?.route != AppRoutes.FAVORITE_SCREEN.toString())

    val context = LocalContext.current
    val sheetState = rememberModalBottomSheetState()
    var showRandomPoemOptions by remember { mutableStateOf(false) }


    LaunchedEffect(key1 = backStackEntry) {
        viewModel.updateBreadCrumbs(backStackEntry)
        viewModel.shouldShowShuffle(backStackEntry)
        viewModel.shouldShowHistory(backStackEntry)
        viewModel.shouldShowRandomOptions(backStackEntry)
    }

    val breadCrumbs = viewModel.breadCrumbs
    val showShuffle = viewModel.showShuffleIcon
    val showHistory = viewModel.showHistoryIcon
    val showRandomOptions = viewModel.showRandomOptions
    val coroutineScope = rememberCoroutineScope()

    BackHandler(enabled = backStackEntry?.destination?.route == AppRoutes.DOWNLOADABLE_POETS_SCREEN.toString()) {
        onBackButtonClicked(backStackEntry, navController)
    }
    Column {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            title = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {

                        if (canPop) {
                            IconButton(
                                onClick = {
                                    onBackButtonClicked(backStackEntry, navController)
                                }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    tint = MaterialTheme.colorScheme.onPrimary,
                                    contentDescription = "Back",
                                    modifier = Modifier
                                        .size(24.dp),
                                )
                            }
                        } else {
                            Image(
                                myIcon,
                                contentDescription = "Logo",
                                modifier = Modifier.size(48.dp),
                                contentScale = ContentScale.Fit,
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = breadCrumbs,
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.White,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.sizeIn(
                                maxWidth = 260.dp
                            ),
                            maxLines = 1
                        )
                    }
                    Row {
                        if (showHistory) {
                            IconButton(modifier = Modifier.introShowCaseTarget(
                                index = 5,
                                style = ShowcaseStyle.Default.copy(
                                    backgroundColor = MaterialTheme.colorScheme.primary,
                                    backgroundAlpha = 0.98f,
                                    targetCircleColor = MaterialTheme.colorScheme.onPrimary
                                ),
                                content = {
                                    ButtonIntro(
                                        stringResource(R.string.INTRO_HISTORY_TITLE),
                                        stringResource(R.string.INTRO_HISTORY_DESC)
                                    )
                                }
                            ), onClick = { navController.navigate("${AppRoutes.HISTORY}") }) {
                                Icon(
                                    imageVector = Icons.Filled.History,
                                    contentDescription = "History",
                                    tint = MaterialTheme.colorScheme.onPrimary,
                                )
                            }
                        }
                        if (showShuffle) {
                            CustomIconButton(
                                modifier = Modifier.introShowCaseTarget(
                                    index = 6,
                                    style = ShowcaseStyle.Default.copy(
                                        backgroundColor = MaterialTheme.colorScheme.primary,
                                        backgroundAlpha = 0.98f,
                                        targetCircleColor = MaterialTheme.colorScheme.onPrimary
                                    ),
                                    content = {
                                        ButtonIntro(
                                            stringResource(R.string.INTRO_RANDOM_TITLE),
                                            stringResource(R.string.INTRO_RANDOM_DESC)
                                        )
                                    }),
                                icon = Icons.Filled.Shuffle,
                                description = stringResource(id = R.string.RANDOM_POEM),
                                onClick = {
                                    coroutineScope.launch {
                                        val poemWithPoet =
                                            viewModel.findShuffledPoem(backStackEntry)
                                        if (poemWithPoet == null) {
                                            Toast.makeText(
                                                context,
                                                context.getString(R.string.NO_POEM_AVAILABLE_FOR_RANDOM),
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        } else {
                                            navController.navigate(
                                                "${AppRoutes.POEM}/${poemWithPoet.poet.id}/${poemWithPoet.poem.id}/-1"
                                            )
                                        }
                                    }
                                },
                                onLongClick = {
                                    showRandomPoemOptions = true
                                }
                            )
                        }
                    }
                }

            },
        )
        AudioControlBar(navController = navController, viewModel = audioViewModel)
        if (showRandomOptions && showRandomPoemOptions) {
            RandomPoemOptions(sheetState = sheetState) {
                showRandomPoemOptions = false
            }
        }
    }
}


private fun onBackButtonClicked(
    backStackEntry: NavBackStackEntry?,
    navController: NavController
) {
    if (backStackEntry?.destination?.route == AppRoutes.DOWNLOADABLE_POETS_SCREEN.toString()) {
        navController.navigate(AppRoutes.DOWNLOADED_POETS_SCREEN.toString()) {
            popUpTo(AppRoutes.DOWNLOADED_POETS_SCREEN.toString()) {
                inclusive = true
            }
            popUpTo(AppRoutes.DOWNLOADABLE_POETS_SCREEN.toString()) {
                inclusive = true
            }
        }
    } else {
        navController.popBackStack()
    }
}
