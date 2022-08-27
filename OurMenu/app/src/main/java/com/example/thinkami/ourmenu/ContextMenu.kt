package com.example.thinkami.ourmenu

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext

@Composable
fun ContextMenu(
    expandedContextMenu: MutableState<Boolean>,
    selectedFood: Food
) {
    val context = LocalContext.current

    DropdownMenu(
        expanded = expandedContextMenu.value,
        onDismissRequest = { expandedContextMenu.value = false }
    ) {
        DropdownMenuItem(
            onClick = { intentEmail(context, selectedFood) },
        ) {
            Text(text="メールする")
        }

        DropdownMenuItem(
            onClick = { intentSms(context, selectedFood) }
        ) {
            Text(text="SMSする")
        }
    }
}

fun intentEmail(context: Context, selectedFood: Food) {
    val uri = Uri.fromParts("mailto", "foo@example.com", null)
    val intent = Intent(Intent.ACTION_SENDTO, uri)
    intent.putExtra(Intent.EXTRA_SUBJECT, "our menu")
    intent.putExtra(Intent.EXTRA_TEXT, "${selectedFood.name}が食べたい！")

    context.startActivity(intent)
}

fun intentSms(context: Context, selectedFood: Food) {
    val uri = Uri.fromParts("smsto", "9999999999", null)
    val intent = Intent(Intent.ACTION_SENDTO, uri)
    intent.putExtra("sms_body", "${selectedFood.name}が食べたい！")

    context.startActivity(intent)
}