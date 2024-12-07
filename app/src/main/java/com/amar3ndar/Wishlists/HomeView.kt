package com.amar3ndar.Wishlists

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberDismissState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.amar3ndar.Wishlists.data.Wish

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeView(
    navController: NavController,
    viewModel: WishViewModel
) {
    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()

    // Check if it's the first launch
    val isFirstLaunch = remember { mutableStateOf(true) }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { AppBarView(title = "WishList") },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(all = 20.dp),
                contentColor = Color.White,
                backgroundColor = Color.Gray,
                onClick = {
                    // Toast.makeText(context, "Floating Action Button", Toast.LENGTH_LONG).show()
                    navController.navigate(Screen.AddScreen.route + "/0")
                }
            ) {
                Icon(imageVector = Icons.Default.AddCircle, contentDescription = "Add")
            }
        },
        backgroundColor = MaterialTheme.colorScheme.background
    ) {
        if (isFirstLaunch.value) {
            LaunchedEffect(Unit) {
                isFirstLaunch.value = false
                scaffoldState.snackbarHostState.showSnackbar(
                    message = "Welcome to your Wishlist App!",
                    actionLabel = "Dismiss",
                    duration = SnackbarDuration.Long
                )
            }
        }

        val wishlist by viewModel.getAllWishes.collectAsState(initial = listOf())

        LazyColumn(modifier = Modifier.fillMaxSize().padding(it)) {
            items(wishlist, key = { wish -> wish.id }) { wish ->
                // Swipe to delete
                val dismissState = rememberDismissState(
                    confirmStateChange = {
                        if (it == DismissValue.DismissedToEnd || it == DismissValue.DismissedToStart) {
                            viewModel.deleteWish(wish)
                        }
                        true
                    }
                )

//delete a wish

                SwipeToDismiss(
                    state = dismissState,
                    background = {
                        val color by animateColorAsState(
                            if (dismissState.dismissDirection == DismissDirection.EndToStart)
                            Color.Red else Color.Transparent
                        )
                        val alignment = Alignment.CenterEnd
                        Box(
                            Modifier
                                .fillMaxSize()
                                .background(color)
                                .padding(horizontal = 20.dp),
                            contentAlignment = alignment
                        ) {
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = "Delete Icon",
                                tint = Color.White
                            )
                        }
                    },
                    directions = setOf(DismissDirection.EndToStart), // E-S -> right to left direction
                    dismissThresholds = { FractionalThreshold(1f) }, // how much swipe for delete
                    dismissContent = {
                        WishItem(wish = wish) {
                            val id = wish.id
                            navController.navigate(Screen.AddScreen.route + "/$id")
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun WishItem(wish: Wish, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 8.dp, end = 8.dp)
            .clickable { onClick() },
        elevation = 20.dp,
        backgroundColor = Color.White,
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = wish.title, fontWeight = FontWeight.ExtraBold)
            Text(text = wish.description)
        }
    }
}

