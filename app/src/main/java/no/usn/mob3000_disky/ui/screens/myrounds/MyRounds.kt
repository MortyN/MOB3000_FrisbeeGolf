package no.usn.mob3000_disky.ui.screens.myrounds

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MyRounds {
    @Preview
    @Composable
    fun ScoreTableContainer() {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(15.dp)
        )
        {
            //ITEM FOR ONE.
            items(5) {

                Box(
                    modifier = Modifier
                        .padding(top = 15.dp)
                        .fillMaxWidth(0.9f)
                        .defaultMinSize(minHeight = 200.dp)
                        .clip(shape = RoundedCornerShape(10))
                        .border(2.dp, color = Color.Black, RoundedCornerShape(10))
                        .background(Color(0xFF005B97))
                        //.background(color = Blue)

                ) {
                    Box(
                        modifier = Modifier
                            .padding(top = 10.dp)
                            .padding(bottom = 10.dp)
                            //.padding( 10.dp)
                            .fillMaxWidth(0.92f)
                            .fillMaxHeight()
                            .align(Alignment.Center)
                            .clip(shape = RoundedCornerShape(8))
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .background(MaterialTheme.colors.background)
                                //.background(color = Yellow)
                        ) {
                            val nameList = listOf<String>("Carl", "Bobby", "Tommy")
                            val scoreList = listOf<Int>(4, 32, 52, 51, 32, 65, 34, 13, 16)
                            val scoreList2 = listOf<Int>(32, 5, 60, 32, 41, 32, 3, 10, 9)
                            //TODO Create a Row with name, score numbers :) - Done
                            //TODO Redo loop: For every name, do a Row
                            //TODO: Figure out why padding ain't doing anything..
                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillParentMaxHeight(0.05f)
                                    //.background(color = Green)
                            ) {
                                Text(
                                    text = "ThisMap", modifier = Modifier, fontSize = 20.sp
                                )
                            }

                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillParentMaxHeight(0.03f)
                                // .background(color = Green)
                            ) {
                                Text(
                                    text = "Round 1 - 9"
                                )
                            }
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillParentMaxHeight(0.025f)
                                    //.border(1.dp, color = Color.Black)
                            ) {
                                Text(
                                    text = "Names",
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .weight(0.2f)
                                        .fillMaxHeight()
                                        //.background(color = Cyan)
                                )
                                for (i in 1..9) {
                                    Text(
                                        text = "$i",
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .weight(0.1f)
                                            //.background(color = Purple200)
                                            .fillMaxHeight()
                                            //.border(1.dp, color = Color.Black)
                                    )
                                }
                            }
                            for (k in nameList) {
                                //TODO: Add name here.
                                Row(
                                    horizontalArrangement = Arrangement.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .fillParentMaxHeight(0.025f)
                                        //.border(1.dp, color = Color.Black)
                                ) {
                                    Text(
                                        text = "$k",
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .weight(0.2f)
                                            .fillMaxHeight()
                                            //.background(color = Cyan)
                                    )
                                    for (j in scoreList) {
                                        //TODO: Add scores here.
                                        Text(
                                            text = "$j",
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier
                                                .weight(0.1f)
                                                .fillMaxHeight()
                                                //       .background(color = Cyan)
                                                //.border(1.dp, color = Color.Black)
                                        )

                                    }


                                }

                            }
                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillParentMaxHeight(0.03f)
                                //.background(color = Green)
                            ) {
                                Text(
                                    text = "Round 10 - 18"

                                )
                            }

                            //#######

                            Row(
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    //.fillMaxHeight(0.05f)
                                    .fillParentMaxHeight(0.025f)
                                    //.border(1.dp, color = Color.Black)
                            ) {
                                Text(
                                    text = "Names",
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .weight(0.2f)
                                        .fillMaxHeight()
                                    //  .background(color = Cyan)
                                )
                                for (i in 10..18) {
                                    Text(
                                        text = "$i",
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .weight(0.1f)
                                            //    .background(color = Purple200)
                                            .fillMaxHeight()
                                            //.border(1.dp, color = Color.Black)
                                    )
                                }
                            }
                            for (k in nameList) {
                                //TODO: Add name here.
                                Row(
                                    horizontalArrangement = Arrangement.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .fillParentMaxHeight(0.025f)
                                        //.fillMaxHeight(0.05f)
                                        //.border(1.dp, color = Color.Black)
                                ) {
                                    Text(
                                        text = "$k",
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .weight(0.2f)
                                            .fillMaxHeight()
                                        //    .background(color = Cyan)
                                    )
                                    for (j in scoreList2) {
                                        //TODO: Add scores here.
                                        Text(
                                            text = "$j",
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier
                                                .weight(0.1f)
                                                .fillMaxHeight()
                                                //      .background(color = Cyan)
                                                //.border(1.dp, color = Color.Black)
                                        )

                                    }


                                }

                            }
                        }

                    }
                }
            }
        }
    }

}