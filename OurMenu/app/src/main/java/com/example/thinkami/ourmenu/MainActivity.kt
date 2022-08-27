package com.example.thinkami.ourmenu

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.thinkami.ourmenu.ui.theme.OurMenuTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            OurMenuTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    HomeScreen()
                }
            }
        }
    }
}

data class Food(var resourceId: Int, var name: String)

@OptIn(ExperimentalFoundationApi::class) // combinedClickableのために必要
@Composable
fun HomeScreen() {
    val (selectedFood, foodSetter) = remember {
        mutableStateOf(Food(R.drawable.tablet_cooking_recipe, "何にしようかな"))
    }
    val expandedContextMenu = remember {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Our Menu")
                },
                backgroundColor = Color.LightGray,
                actions = {
                    IconButton(onClick = {
                        foodSetter(selectedFood.copy(
                            resourceId = R.drawable.tablet_cooking_recipe,
                            name = "何にしようかな"
                        ))
                    }) {
                        Icon(
                            Icons.Filled.Home,
                            contentDescription = "more",
                            modifier = Modifier.size(20.dp),
                        )
                    }

                    TopAppBarDropdownMenu(
                        selectedFood = selectedFood,
                        foodSetter = foodSetter
                    )
                }
            )
        },
        content = {
            Box {
                Image(
                    painter = painterResource(id = R.drawable.gohan_background),
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxSize()
                        .fillMaxWidth(),
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 300.dp,
                        ),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = selectedFood.name,
                        modifier = Modifier.padding(
                            start = 30.dp
                        )
                    )

                    Image(
                        painter = painterResource(id = selectedFood.resourceId),
                        contentDescription = "image",
                        modifier = Modifier
                            .padding(
                                top = 30.dp
                            )
                            .width(250.dp)
                            .height(250.dp)
                            .combinedClickable(
                                enabled = true,
                                onClick = {},
                                onLongClick = {
                                    // 晩ごはんが選択されていたら、コンテキストメニューを表示する
                                    if (selectedFood.resourceId != R.drawable.tablet_cooking_recipe) {
                                        expandedContextMenu.value = true
                                    }
                                }
                            )
                    )

                    ContextMenu(
                        expandedContextMenu = expandedContextMenu,
                        selectedFood = selectedFood
                    )
                }
            }
        }
    )
}

