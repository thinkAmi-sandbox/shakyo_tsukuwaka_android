package com.example.thinkami.outofbusinesscard

import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.thinkami.outofbusinesscard.ui.theme.OutOfBusinessCardTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OutOfBusinessCardTheme {
                // 共有プリファレンスで this を使うため、ここで取得しておく
                val pref = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this)

                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    MainApp(pref)
                }
            }
        }
    }
}

@Composable
fun MainApp(pref: SharedPreferences) {
    // ナビゲーション設定
    val navController = rememberNavController()

    NavHost(navController, startDestination = "main") {
        composable("main") { HomeScreen(navController, pref) }
        composable("edit") { EditScreen(navController, pref) }
    }
}


@Composable
fun HomeScreen(navController: NavController, pref: SharedPreferences) {
    // 画面を landscape に固定
    LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)

    val companyName by rememberSaveable {
        mutableStateOf(pref.getString("companyName", "") ?: "")
    }
    val postal by rememberSaveable {
        mutableStateOf(pref.getString("postal", "") ?: "")
    }
    val address by rememberSaveable {
        mutableStateOf(pref.getString("address", "") ?: "")
    }
    val tel by rememberSaveable {
        mutableStateOf(pref.getString("tel", "") ?: "")
    }
    val fax by rememberSaveable {
        mutableStateOf(pref.getString("fax", "") ?: "")
    }
    val email by rememberSaveable {
        mutableStateOf(pref.getString("email", "") ?: "")
    }
    val url by rememberSaveable {
        mutableStateOf(pref.getString("url", "") ?: "")
    }
    val position by rememberSaveable {
        mutableStateOf(pref.getString("position", "") ?: "")
    }
    val name by rememberSaveable {
        mutableStateOf(pref.getString("name", "") ?: "")
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text("Out of Business Card") 
                },
                actions = {
                    IconButton(onClick = {
                        navController.navigate("edit")
                    }) {
                        Icon(Icons.Filled.Edit, contentDescription = "編集")
                    }
                },
            )
        },
        content = {
            Column {
                Text(
                    text = position,
                    modifier = Modifier.padding(top = 80.dp, start = 500.dp)
                )

                Row(
                    modifier = Modifier.padding(top = 10.dp)
                ) {
                    Text(
                        text = companyName,
                        fontSize = 25.sp,
                        modifier = Modifier.padding(start = 30.dp)
                    )
                    Text(
                        text = name,
                        fontSize = 25.sp,
                        modifier = Modifier.padding(start = 300.dp)
                    )
                }

                Row(
                    modifier = Modifier.padding(top = 10.dp)
                ) {
                    Text(
                        text = postal,
                        modifier = Modifier.padding(start = 30.dp)
                    )
                    Text(
                        text = address,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                }

                Row {
                    Text(
                        text = "tel:",
                        modifier = Modifier.padding(start = 30.dp)
                    )
                    Text(
                        text = tel,
                    )

                    Text(
                        text = "fax: ",
                        modifier = Modifier.padding(start = 10.dp)
                    )

                    Text(
                        text = fax,
                    )
                }

                Row {
                    Text(
                        text = email,
                        modifier = Modifier.padding(start = 30.dp)
                    )

                    Text(
                        text = url,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                }
            }
        })
}
