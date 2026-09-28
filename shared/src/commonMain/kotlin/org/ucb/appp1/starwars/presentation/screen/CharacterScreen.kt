package org.ucb.appp1.starwars.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import org.ucb.appp1.starwars.presentation.composable.CharacterItem
import org.ucb.appp1.starwars.presentation.viewmodel.CharacterEffect
import org.ucb.appp1.starwars.presentation.viewmodel.CharacterEvent
import org.ucb.appp1.starwars.presentation.viewmodel.CharacterState
import org.ucb.appp1.starwars.presentation.viewmodel.CharacterViewModel

@Composable
fun CharacterScreen(
    onBack: () -> Unit = {},
    viewModel: CharacterViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.emitEvent(CharacterEvent.OnLoad)
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is CharacterEffect.ShowMessage -> snackbarHostState.showSnackbar(effect.message)
            }
        }
    }

    CharacterContent(
        state = state,
        snackbarHostState = snackbarHostState,
        onEvent = viewModel::emitEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CharacterContent(
    state: CharacterState,
    snackbarHostState: SnackbarHostState,
    onEvent: (CharacterEvent) -> Unit
) {
    val darkBackground = Color(0xFF0B0B10)
    val starWarsYellow = Color(0xFFFFE81F)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Star Wars - Personajes",
                            color = starWarsYellow,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        if (state.totalCount > 0) {
                            Text(
                                text = "Mostrando ${state.characters.size} de ${state.totalCount} (Página ${state.currentPage})",
                                color = Color.LightGray,
                                fontSize = 11.sp
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF151520)
                )
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = darkBackground
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                state.isLoading && state.characters.isEmpty() -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = starWarsYellow
                    )
                }
                state.error != null && state.characters.isEmpty() -> {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = state.error, color = Color.White)
                        Spacer(modifier = Modifier.height(12.dp))
                        Button(
                            onClick = { onEvent(CharacterEvent.OnRetry) },
                            colors = ButtonDefaults.buttonColors(containerColor = starWarsYellow)
                        ) {
                            Text("Reintentar", color = Color.Black, fontWeight = FontWeight.Bold)
                        }
                    }
                }
                else -> {
                    LazyColumn(
                        contentPadding = PaddingValues(vertical = 8.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(
                            items = state.characters,
                            key = { it.name }
                        ) { character ->
                            CharacterItem(
                                character = character,
                                onClick = { onEvent(CharacterEvent.OnSelectCharacter(character)) }
                            )
                        }

                        // Pagination Footer
                        if (state.hasNextPage) {
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (state.isMoreLoading) {
                                        CircularProgressIndicator(color = starWarsYellow)
                                    } else {
                                        Button(
                                            onClick = { onEvent(CharacterEvent.OnLoadNextPage) },
                                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2D2B3D))
                                        ) {
                                            Text(
                                                text = "Cargar más personajes (Página ${state.currentPage + 1})",
                                                color = Color.White
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
