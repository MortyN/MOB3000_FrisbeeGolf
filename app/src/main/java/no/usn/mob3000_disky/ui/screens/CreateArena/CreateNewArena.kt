package no.usn.mob3000_disky.ui.screens.CreateArena

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class CreateNewArena {
    @Preview
    @Composable
    fun ArenaForm() {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Box(modifier = Modifier
                .fillMaxWidth(0.9f)
                .fillMaxHeight(0.8f)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(15.dp)
                ) {
                    var nameText by remember {
                        mutableStateOf("")
                    }
                    TextField(value = nameText,
                        onValueChange = { newText -> nameText = newText },
                        label = { Text(text = "Arena name") },
                        maxLines = 1,
                        modifier = Modifier
                            .fillMaxWidth()
                            //.height(60.dp)
                            .padding(bottom = 15.dp)
                    )
                    var descText by remember {
                        mutableStateOf("")
                    }
                    TextField(value = descText,
                        onValueChange = { newText -> descText = newText },
                        label = { Text(text = "Description") },
                        maxLines = 5,
                        modifier = Modifier
                            .fillMaxWidth()
                            .defaultMinSize(minHeight = 100.dp)
                            .padding(bottom = 15.dp)
                    )
                    Button(onClick = { /*TODO send user to Google Map, or replace this button with Google Maps*/ },
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .height(120.dp)
                            .padding(bottom = 20.dp)
                    ) {
                        Text(text = "Add location", fontSize = 20.sp)
                    }

                    Text(text = "Change map status")

                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                    ) {
                        Button(onClick = { /*TODO Change color to gray and change value to true.*/ },
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green),
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .height(80.dp)
                                .weight(0.5f)
                        ) {
                            Text(text = "Active", fontSize = 20.sp)
                        }

                        Button(onClick = { /*TODO Change color to gray and change value to false.*/ },
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .height(80.dp)
                                .weight(0.5f)

                        ) {
                            Text(text = "Inactive", fontSize = 20.sp)
                        }
                    }

                }
            }
        }

    }
}