package no.usn.mob3000_disky.ui.screens.MyArenas

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class ShowMyArenas {
    @Preview
    @Composable
    fun MyArenas() {
        //TODO: Name of the map, area.
        //TODO: Created date, last edited.
        //TODO: Maps area.
        //TODO: Description of the map


        //TODO: FIrst name of map + area, then image, then date, THEN desc

        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
            //.background(color = Red)
        ) {
            items(5) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .defaultMinSize(200.dp)
                        //.background(color = Cyan)
                        .padding(top = 15.dp)
                        .padding(bottom = 15.dp)
                        .border(1.dp, Color.Black)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .defaultMinSize(minHeight = 100.dp)
                        //.background(color = Blue)
                    ) {

                        Text(
                            text = "Name - Area",
                            fontSize = 20.sp,
                            modifier = Modifier
                                //.background(color = Red)
                                .padding(top = 5.dp)
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(0.6f)
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight()
                            ) {
                                Text(
                                    text = "IMAGE HERE",
                                    fontSize = 25.sp,
                                    modifier = Modifier.border(1.dp, Color.Black)
                                )
                            }

                        }
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .defaultMinSize(minHeight = 14.dp)
                                //.background(color = Red)
                                .padding(top = 5.dp)
                        ) {
                            Text(
                                text = "Created: 11/10/21",
                                textAlign = TextAlign.Center,
                                fontSize = 10.sp,
                                modifier = Modifier
                                    .weight(0.5f)
                                    .fillMaxHeight()
                            )
                            Text(
                                text = "Last updated: 12/10/21",
                                textAlign = TextAlign.Center,
                                fontSize = 10.sp,
                                modifier = Modifier
                                    .weight(0.5f)
                                    .fillMaxHeight()
                            )
                        }

                        Box(modifier = Modifier
                            .padding(10.dp)
                        ) {
                            Text(
                                text = "Hello everyone. Today is the day of the day I'm saying. Indeed it's the day. Today's the day. yeah. *Skuuurt* Rap rap lyrics. Yeah. You know who it is.",
                                fontSize = 12.sp
                            )
                        }

                    }
                }
            }
        }
    }

}