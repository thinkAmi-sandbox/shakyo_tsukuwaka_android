package com.example.thinkami.outofbusinesscard

import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun EditScreen(navController: NavController, pref: SharedPreferences) {
    // 画面を portrait に固定
    LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

    var companyName by rememberSaveable {
        mutableStateOf(pref.getString("companyName", "") ?: "")
    }
    var postal by rememberSaveable {
        mutableStateOf(pref.getString("postal", "") ?: "")
    }
    var address by rememberSaveable {
        mutableStateOf(pref.getString("address", "") ?: "")
    }
    var tel by rememberSaveable {
        mutableStateOf(pref.getString("tel", "") ?: "")
    }
    var fax by rememberSaveable {
        mutableStateOf(pref.getString("fax", "") ?: "")
    }
    var email by rememberSaveable {
        mutableStateOf(pref.getString("email", "") ?: "")
    }
    var url by rememberSaveable {
        mutableStateOf(pref.getString("url", "") ?: "")
    }
    var position by rememberSaveable {
        mutableStateOf(pref.getString("position", "") ?: "")
    }
    var name by rememberSaveable {
        mutableStateOf(pref.getString("name", "") ?: "")
    }

    Column{
        TextField(
            value = companyName,
            onValueChange = { companyName = it },
            label = { Text("会社名") },
            modifier = Modifier
                .width(320.dp)
                .padding(top = 16.dp),
        )

        TextField(
            value = postal,
            onValueChange = { postal = it },
            label = { Text("郵便番号") },
            modifier = Modifier
                .width(160.dp)
                .padding(top = 16.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        TextField(
            value = address,
            onValueChange = { address = it },
            label = { Text("住所") },
            modifier = Modifier
                .width(350.dp)
                .padding(top = 16.dp),
        )

        TextField(
            value = tel,
            onValueChange = { tel = it },
            label = { Text("電話番号") },
            modifier = Modifier.padding(top = 16.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
        )

        TextField(
            value = fax,
            onValueChange = { fax = it },
            label = { Text("FAX") },
            modifier = Modifier.padding(top = 16.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
        )

        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("メールアドレス") },
            modifier = Modifier.padding(top = 16.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        TextField(
            value = url,
            onValueChange = { url = it },
            label = { Text("URL") },
            modifier = Modifier.padding(top = 16.dp),
        )

        TextField(
            value = position,
            onValueChange = { position = it },
            label = { Text("部署 & 役職") },
            modifier = Modifier.padding(top = 16.dp),
        )

        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("名前") },
            modifier = Modifier.padding(top = 16.dp),
        )

        Row(
            modifier = Modifier.padding(top = 24.dp),
        ) {
            Button(onClick = {
                // データを保存する
                val editor = pref.edit()
                editor.putString("companyName", companyName)
                    .putString("postal", postal)
                    .putString("address", address)
                    .putString("tel", tel)
                    .putString("fax", fax)
                    .putString("email", email)
                    .putString("url", url)
                    .putString("position", position)
                    .putString("name", name)
                    .apply()

                // メイン画面へ戻る
                navController.navigate("main")
            }) {
                Text("保存")
            }

            Button(
                modifier = Modifier.padding(
                    start = 16.dp
                ),
                onClick = {
                    // メイン画面へ戻る
                    navController.navigate("main")
                }
            ) {
                Text("キャンセル")
            }
        }
    }
}

