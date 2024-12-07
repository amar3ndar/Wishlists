package com.amar3ndar.Wishlists

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun AppBarView(
    title: String,
    onBackNavClicked: () -> Unit = {} // Changed to a regular function type
) {

//Below is the code from ChatGPT for the status bar
    // Initialize System UI Controller
    val systemUiController = rememberSystemUiController()
    // Set status bar color to transparent or system default
    systemUiController.setStatusBarColor(
        color = colorResource(id = R.color.background_dark),  // Status bar color
        darkIcons = false
        //false -> white text , true -> black text
    )
//to here

    val navigationIcon: (@Composable () -> Unit)? =        //nullable neither input nor output
        if (!title.contains("WishList")) {
            {
                IconButton(onClick = { onBackNavClicked() }) {   //Content
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        tint = Color.White,
                        contentDescription = "Back"
                    )
                }
            }
        } else {
            null
        }
//Content
//Title font Style


    val godOfWar = FontFamily(
        Font(R.font.god_of_war),
    )

    val title_TextStyle = TextStyle(
        fontFamily = godOfWar, //font family (custom font)
        fontSize = 25.sp, //font Size
        color = Color.White //Font color
    )

    TopAppBar(
        modifier = Modifier.statusBarsPadding().fillMaxWidth()
            .clip(RoundedCornerShape(16.dp)),
        title = {
            Text(
                text = title,
                style = title_TextStyle,
                modifier = Modifier.padding(start = 15.dp).heightIn(max = 24.dp)
            )
        },
        elevation = 3.dp,
        backgroundColor = colorResource(id = R.color.app_bar_color),
        navigationIcon = navigationIcon,
        contentColor = Color.White
    )
}