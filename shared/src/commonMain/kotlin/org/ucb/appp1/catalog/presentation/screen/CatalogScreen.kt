package org.ucb.appp1.catalog.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import org.ucb.appp1.catalog.presentation.composable.MovieItem
import org.ucb.appp1.catalog.presentation.viewmodel.CatalogEffect
import org.ucb.appp1.catalog.presentation.viewmodel.CatalogEvent
import org.ucb.appp1.catalog.presentation.viewmodel.CatalogState
import org.ucb.appp1.catalog.presentation.viewmodel.CatalogViewModel

@Composable
fun CatalogScreen(
    onNavigateToDetail: (Int) -> Unit,
    viewModel: CatalogViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.emitEvent(CatalogEvent.OnLoad)
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is CatalogEffect.ShowMessage -> snackbarHostState.showSnackbar(effect.message)
                is CatalogEffect.NavigateToDetail -> onNavigateToDetail(effect.id)
            }
        }
    }

    CatalogContent(
        state = state,
        snackbarHostState = snackbarHostState,
        onEvent = viewModel::emitEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CatalogContent(
    state: CatalogState,
    snackbarHostState: SnackbarHostState,
    onEvent: (CatalogEvent) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Movie Catalog") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                state.isLoading && state.movies.isEmpty() -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                state.error != null && state.movies.isEmpty() -> {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = state.error)
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = { onEvent(CatalogEvent.OnRetry) }) {
                            Text("Reintentar")
                        }
                    }
                }
                else -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        contentPadding = PaddingValues(8.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(
                            items = state.movies,
                            key = { it.id }
                        ) { movie ->
                            MovieItem(
                                movie = movie,
                                onClick = { onEvent(CatalogEvent.OnShowDetail(movie.id)) }
                            )
                        }
                    }
                }
            }
        }
    }
}
