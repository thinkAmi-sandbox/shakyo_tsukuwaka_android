package com.example.thinkami.ourmenu

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun MainMenu(
    expandedMain: MutableState<Boolean>,
    expandedCurry: MutableState<Boolean>,
    expandedRamen: MutableState<Boolean>,
) {
    DropdownMenu(
        expanded = expandedMain.value,
        onDismissRequest = { expandedMain.value = false }
    ) {
        DropdownMenuItem(
            onClick = {
                expandedMain.value = false
                expandedCurry.value = true
                expandedRamen.value = false
            }
        ) {
            Text("カレー")
            Icon(
                Icons.Filled.PlayArrow,
                contentDescription = "more",
                modifier = Modifier.size(20.dp)
            )
        }

        DropdownMenuItem(
            onClick = {
                expandedMain.value = false
                expandedCurry.value = false
                expandedRamen.value = true
            }
        ) {
            Text("ラーメン")
            Icon(
                Icons.Filled.PlayArrow,
                contentDescription = "more",
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
fun NestedMenu(
    expandedNested: MutableState<Boolean>,
    resourceIds: List<Food>,
    selectedFood: Food,
    foodSetter: (Food) -> Unit
) {
    DropdownMenu(
        expanded = expandedNested.value,
        onDismissRequest = {
           expandedNested.value = false
        }) {

        resourceIds.forEach {
            DropdownMenuItem(
                onClick = {
                    expandedNested.value = false
                    foodSetter(selectedFood.copy(
                        resourceId = it.resourceId,
                        name = it.name
                    ))
                }
            ) {
                Text(text = it.name)
            }
        }
    }
}

@Composable
fun TopAppBarDropdownMenu(
    selectedFood: Food,
    foodSetter: (Food) -> Unit
) {
    // mutableStateとして渡したいため、 by を使わない
    val expandedMain = remember {
        mutableStateOf(false)
    }
    val expandedCurry = remember {
        mutableStateOf(false)
    }
    val expandedRamen = remember {
        mutableStateOf(false)
    }
    
    Box(
        modifier = Modifier.wrapContentSize(Alignment.TopEnd)
    ) {
        IconButton(onClick = {
            expandedMain.value = true
            expandedCurry.value = false
            expandedRamen.value = false
        }) {
            Icon(
                Icons.Filled.MoreVert,
                contentDescription = "more"
            )
        }

        MainMenu(
            expandedMain = expandedMain,
            expandedCurry = expandedCurry,
            expandedRamen = expandedRamen
        )

        NestedMenu(
            expandedNested = expandedCurry,
            resourceIds = listOf(
                Food(R.drawable.food_curryruce, "カレーライス"),
                Food(R.drawable.food_drycurry, "ドライカレー"),
                Food(R.drawable.food_curry_matsaman, "マッサマンカレー"),
            ),
            selectedFood = selectedFood,
            foodSetter = foodSetter
        )

        NestedMenu(
            expandedNested = expandedRamen,
            resourceIds = listOf(
                Food(R.drawable.ramen_syouyu, "醤油ラーメン"),
                Food(R.drawable.ramen_tonkotsu, "豚骨ラーメン"),
                Food(R.drawable.food_ramen_iekei, "家系ラーメン"),
            ),
            selectedFood = selectedFood,
            foodSetter = foodSetter
        )
    }
}
        