package com.example.thinkami.bloodpressure

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.thinkami.bloodpressure.model.WeightRecord
import com.example.thinkami.bloodpressure.ui.theme.BloodPressureTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.format.DateTimeFormatter

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BloodPressureTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    MainApp()
                }
            }
        }
    }
}

@Composable
fun MainApp() {
    val (showSnackBar, setShowSnackBar) = remember {
        mutableStateOf(false)
    }

    val (message, setMessage) = remember {
        mutableStateOf("")
    }

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            HomeScreen(
                navController = navController,
                message = message,
                setMessage = setMessage,
                showSnackBar = showSnackBar,
                setShowSnackBar = setShowSnackBar
            )
        }
        composable(
            "edit?recordId={recordId}",
            arguments = listOf(navArgument("recordId") { defaultValue = "" })
        ) { backStackEntry ->
            backStackEntry.arguments?.getString("recordId")?.let { it ->
                EditScreen(
                    navController = navController,
                    setShowSnackBar = setShowSnackBar,
                    setMessage = setMessage,
                    recordId = it
                )
            }
        }
    }
}

@Composable
fun HomeScreen(
    navController: NavController,
    message: String,
    setMessage: (String) -> Unit,
    showSnackBar: Boolean,
    setShowSnackBar: (Boolean) -> Unit,
) {
    var weightRecords: List<WeightRecord>? by rememberSaveable {
        mutableStateOf(null)
    }

    LaunchedEffect(Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.Default) {
                val dao = MyApplication.database.weightRecordDao()
                weightRecords = dao.getAll()
            }
        }
    }

    val scaffoldState = rememberScaffoldState()
    if (showSnackBar) {
        LaunchedEffect(scaffoldState.snackbarHostState) {
            val result = scaffoldState.snackbarHostState.showSnackbar(
                message = message,
                actionLabel = "閉じる"
            )

            when (result) {
                SnackbarResult.Dismissed -> { setShowSnackBar(false) }
                SnackbarResult.ActionPerformed -> { setShowSnackBar(false) }
            }
        }
    }


    Scaffold(
        scaffoldState = scaffoldState,  // snackbarを表示するために scaffoldState をセット
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Weight Record")
                },
                actions = {
                    IconButton(onClick = {
                        // 全件削除する
                        CoroutineScope(Dispatchers.Main).launch {
                            withContext(Dispatchers.Default) {
                                val dao = MyApplication.database.weightRecordDao()
                                dao.deleteAll()

                                // 画面の表示も削除するため、 null をセットしておく
                                weightRecords = null
                            }
                        }

                        setMessage("すべて削除しました")
                        setShowSnackBar(true)
                    }) {
                        Icon(
                            Icons.Filled.Delete,
                            contentDescription = "全削除"
                        )
                    }
                }
            )
        },
        content = { padding ->
            WeightRecordList(
                navController = navController,
                paddingValues = padding,
                weightRecords = weightRecords
            )
        },
        floatingActionButton = {
            AddButton(navController)
        }
    )
}


@Composable
fun AddButton(navController: NavController) {
    FloatingActionButton(onClick = { navController.navigate("edit") }) {
        Icon(Icons.Filled.Add, contentDescription = "追加")
    }
}

@Composable
fun WeightRecordList(navController: NavController, paddingValues: PaddingValues, weightRecords: List<WeightRecord>?) {
    if (weightRecords == null) {
        return
    }

    val dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.padding(paddingValues)
    ) {
        items(weightRecords) { weightRecord ->
            Row(modifier = Modifier.padding(all = 8.dp)) {
                Column(
                    modifier = Modifier.clickable {
                        navController.navigate("edit?recordId=${weightRecord.id}")
                    }
                ) {
                    weightRecord.recordedAt?.let { Text(text= it.format(dtf)) }
                    Text(text="No. ${weightRecord.id}")
                    Text(text="Weight: ${weightRecord.weight}")
                }

            }
        }
    }
}