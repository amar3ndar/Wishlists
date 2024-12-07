package com.amar3ndar.Wishlists

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.amar3ndar.Wishlists.data.Wish
import kotlinx.coroutines.launch

@Composable
fun AddEditDetailView(
    id: Long,
    viewModel: WishViewModel,
    navController: NavController
) {
    val snackMessage = remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    if (id != 0L) {
        val wish = viewModel.getAWishById(id).collectAsState(initial = null)
        wish.value?.let {
            viewModel.wishtTitleState = it.title
            viewModel.wishDescriptionState = it.description
        }
    } else {
        viewModel.wishtTitleState = ""
        viewModel.wishDescriptionState = ""
    }

    // Apply the background color from the current theme
    Surface(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)
        // Apply theme-based background
    ) {
        Scaffold(
            topBar = {
                AppBarView(
                    title =
                    if (id != 0L) stringResource(id = R.string.update_wish)
                    else stringResource(id = R.string.add_wish)
                ) { navController.navigateUp() }
            },
            scaffoldState = scaffoldState
        ) { paddingValues ->
            Column(
                modifier = Modifier.fillMaxSize().padding(paddingValues)
                    .background(MaterialTheme.colorScheme.background),//added background color
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.height(10.dp))

                WishTextField(
                    label = "Title",
                    value = viewModel.wishtTitleState,
                    onValueChange = { viewModel.onWishTitleChanged(it) }
                )

                Spacer(modifier = Modifier.height(10.dp))

                WishTextField(
                    label = "Description",
                    value = viewModel.wishDescriptionState,
                    onValueChange = { viewModel.onWishDescriptionChanged(it) }
                )

                Spacer(modifier = Modifier.height(15.dp))

                Button(onClick = {
                    if (viewModel.wishtTitleState.isNotEmpty() && viewModel.wishDescriptionState.isNotEmpty()) {
                        if (id != 0L) {
                            viewModel.updateWish(
                                Wish(
                                    id = id,
                                    title = viewModel.wishtTitleState.trim(),
                                    description = viewModel.wishDescriptionState.trim()
                                )
                            )
                        } else {
                            viewModel.addWish(
                                Wish(
                                    title = viewModel.wishtTitleState.trim(),
                                    description = viewModel.wishDescriptionState.trim()
                                )
                            )
                            snackMessage.value = "Wish has been created"
                        }
                    } else {
                        snackMessage.value = "Enter fields to create a wish"
                    }
                    scope.launch { navController.navigateUp() }
                }) {
                    Text(
                        text =
                        if (id != 0L) stringResource(id = R.string.update_wish)
                        else stringResource(id = R.string.add_wish),
                        style = TextStyle(fontSize = 18.sp)
                    )
                }
            }
        }
    }
}
       
@Composable
fun WishTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
) {
    val inputTextStyle = TextStyle(
        fontFamily = FontFamily.Serif, fontSize = 20.sp, color = MaterialTheme.colorScheme.onBackground
    )


    //enter text for title and description
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label, style = inputTextStyle) },
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
            .background(MaterialTheme.colorScheme.background), //added this line
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = colorResource(id = R.color.red),
            unfocusedBorderColor = MaterialTheme.colorScheme.onBackground,
            cursorColor = colorResource(id = R.color.red),
            focusedLabelColor = colorResource(id = R.color.red),
            unfocusedLabelColor = MaterialTheme.colorScheme.onBackground,
            backgroundColor = Color.Transparent, // Keep text field background transparent
            textColor = MaterialTheme.colorScheme.onBackground //user input text color
        )
    )
}
