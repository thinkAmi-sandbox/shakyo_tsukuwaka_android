package com.example.thinkami.highandlow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable

// by を使うために、getValueとsetValueを定義
// https://developer.android.com/jetpack/compose/state?hl=ja
import androidx.compose.runtime.getValue

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.thinkami.highandlow.ui.theme.HighAndLowTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HighAndLowTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    HomeScreen()
                }
            }
        }
    }
}


@Composable
fun HomeScreen() {
    var yourCard by rememberSaveable {
        mutableStateOf(drawCard())
    }
    var droidCard by rememberSaveable {
        mutableStateOf(drawCard())
    }
    var hitCount by rememberSaveable {
        mutableStateOf(0)
    }
    var loseCount by rememberSaveable {
        mutableStateOf(0)
    }

    var resultText by rememberSaveable {
        mutableStateOf("結果はこちら")
    }

    var yourCardImageId by rememberSaveable {
        mutableStateOf(showYourCard(yourCard))
    }
    var droidCardImageId by rememberSaveable {
        mutableStateOf(R.drawable.z01)
    }

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text("High and Low")
            })
        },
        // 今回は ConstraintLayout を使わずに実装
        content = {
            Column(
                modifier = Modifier.padding(
                    top = 16.dp
                )
            ) {
                Row {
                    Text(
                        text = "あたり${hitCount}",
                        modifier = Modifier.padding(
                            start = 50.dp,
                        )
                    )

                    Text(
                        text = "はずれ${loseCount}",
                        modifier = Modifier.padding(
                            start = 150.dp,
                        )
                    )
                }

                Column(
                    modifier = Modifier.fillMaxWidth().padding(
                        top = 16.dp,
                        bottom = 76.dp
                    ),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = resultText,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                Row(
                    modifier = Modifier.padding(
                        top = 20.dp
                    )
                ) {
                    Image(
                        painter = painterResource(id = yourCardImageId),
                        contentDescription = "your Card Image",
                        modifier = Modifier
                            .padding(
                                start = 40.dp
                            )
                            .width(140.dp)
                            .height(210.dp)
                    )

                    Image(
                        painter = painterResource(id = droidCardImageId),
                        contentDescription = "droid Card Image",
                        modifier = Modifier
                            .padding(
                                start = 40.dp
                            )
                            .width(140.dp)
                            .height(210.dp)
                    )
                }

                Spacer(modifier = Modifier
                    .height(50.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = {
                            yourCard = drawCard()
                            droidCard = drawCard()

                            yourCardImageId = showYourCard(yourCard)

                            droidCardImageId = hideDroidCard()
                                  },
                        enabled = hitCount < 5 && loseCount < 5
                        ) {
                        Text(text = "次へ")
                    }
                }

                Row(
                    modifier = Modifier.padding(
                        top = 16.dp
                    )
                ) {
                    Button(
                        onClick = {
                            droidCardImageId = showDroidCard(droidCard)

                            val result = highAndLow('h', yourCard, droidCard)
                            if (result > 0) {
                                hitCount++
                            }
                            if (result < 0) {
                                loseCount++
                            }

                            if (hitCount == 5) {
                                resultText = "あなたの勝ちです"
                            } else if (loseCount == 5) {
                                resultText = "あなたの負けです"
                            }
                        },
                        modifier = Modifier.padding(
                            start = 20.dp
                        ),
                        enabled = hitCount < 5 && loseCount < 5

                    ) {
                        Text(text = "High")
                    }
                    Button(
                        onClick = {
                            droidCardImageId = showDroidCard(droidCard)

                            val result = highAndLow('l', yourCard, droidCard)
                            if (result > 0) {
                                hitCount++
                            }
                            if (result < 0) {
                                loseCount++
                            }

                            if (hitCount == 5) {
                                resultText = "あなたの勝ちです"
                            } else if (loseCount == 5) {
                                resultText = "あなたの負けです"
                            }
                        },
                        modifier = Modifier.padding(
                            start = 220.dp
                        ),
                        enabled = hitCount < 5 && loseCount < 5
                    ) {
                        Text(text = "Low")
                    }

                }
            }
        }
    )
}


private fun showYourCard(cardNo: Int): Int  {
    return when(cardNo) {
        1 -> R.drawable.d01
        2 -> R.drawable.d02
        3 -> R.drawable.d03
        4 -> R.drawable.d04
        5 -> R.drawable.d05
        6 -> R.drawable.d06
        7 -> R.drawable.d07
        8 -> R.drawable.d08
        9 -> R.drawable.d09
        10 -> R.drawable.d10
        11 -> R.drawable.d11
       else -> R.drawable.d12
   }
}

private fun drawCard(): Int {
    return (1..13).random()
}

private fun highAndLow(answer: Char, yourCard: Int, droidCard: Int): Int {
    val balance = yourCard - droidCard

    if (balance == 0) {
        return 0
    }

    if (balance > 0 && answer == 'h') {
        return 1
    }

    if (balance < 0 && answer == 'l') {
        return 1
    }

    return -1
}

private fun showDroidCard(cardNo: Int): Int {
    return when(cardNo) {
        1 -> R.drawable.c01
        2 -> R.drawable.c02
        3 -> R.drawable.c03
        4 -> R.drawable.c04
        5 -> R.drawable.c05
        6 -> R.drawable.c06
        7 -> R.drawable.c07
        8 -> R.drawable.c08
        9 -> R.drawable.c09
        10 -> R.drawable.c10
        11 -> R.drawable.c11
        else -> R.drawable.c12
    }
}

private fun hideDroidCard(): Int {
    return R.drawable.z01
}