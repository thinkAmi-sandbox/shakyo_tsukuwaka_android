package com.example.thinkami.bloodpressure

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.thinkami.bloodpressure.model.WeightRecord
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime


@Composable
fun EditScreen(
    navController: NavController,
    setShowSnackBar: (Boolean) -> Unit,
    setMessage: (String) -> Unit,
    recordId: String
) {
    var weight by rememberSaveable {
        mutableStateOf("")
    }

    if (recordId.isNotEmpty()) {
        LaunchedEffect(Unit) {
            CoroutineScope(Dispatchers.Main).launch {
                withContext(Dispatchers.Default) {
                    val dao = MyApplication.database.weightRecordDao()
                    val record = dao.findById(recordId.toInt())
                    weight = record.weight.toString()
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Weight Record")
                },
                actions = {

                }
            )
        },
        bottomBar = {
            BottomAppBar {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    IconButton(onClick = { navController.navigate("main") }) {
                        Icon(Icons.Filled.Home, contentDescription = "ホーム画面へ")
                    }
                }
            }
        },
        content = { padding ->
            Column(
                modifier = Modifier.padding(padding)
            ) {
                TextField(
                    value = weight,
                    onValueChange = { weight = it },
                    label = { Text(text = "体重") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                Row() {
                    Button(onClick = {
                        // データを保存する
                        CoroutineScope(Dispatchers.Main).launch {
                            withContext(Dispatchers.Default) {
                                val dao = MyApplication.database.weightRecordDao()

                                if (recordId.isBlank()) {
                                    dao.insert(WeightRecord(
                                        id = 0,
                                        weight = weight.toInt(),
                                        recordedAt = LocalDateTime.now()))

                                    setMessage("保存しました")
                                    setShowSnackBar(true)
                                } else {
                                    dao.update(recordId.toInt(), weight = weight.toInt())

                                    setMessage("更新しました")
                                    setShowSnackBar(true)
                                }
                            }
                        }

                        // メイン画面へ戻る
                        navController.navigate("main")

                    }) {
                        Text("保存")
                    }

                    if (recordId.isNotBlank()) {
                        Spacer(modifier = Modifier.padding(all = 10.dp))

                        Button(onClick = {
                            // データを削除する
                            CoroutineScope(Dispatchers.Main).launch {
                                withContext(Dispatchers.Default) {
                                    val dao = MyApplication.database.weightRecordDao()
                                    dao.delete(recordId.toInt())

                                    setMessage("削除しました")
                                    setShowSnackBar(true)
                                }
                            }

                            // メイン画面へ戻る
                            navController.navigate("main")
                        }) {
                            Text("削除")
                        }
                    }
                }
            }
        },
    )
}
