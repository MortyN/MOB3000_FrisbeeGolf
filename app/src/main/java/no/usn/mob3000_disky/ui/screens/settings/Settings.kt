package no.usn.mob3000_disky.ui.screens.settings

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Pending
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.size.Scale
import coil.transform.CircleCropTransformation
import kotlinx.coroutines.launch
import no.usn.mob3000_disky.R
import no.usn.mob3000_disky.api.APIUtils
import no.usn.mob3000_disky.model.User
import org.apache.commons.io.FileUtils
import java.io.File
import java.util.*

@ExperimentalUnitApi
@ExperimentalCoilApi
@Composable
fun SettingsScreen(
    settingsViewModel: SettingsViewModel
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val openFileLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.OpenDocument()) { task ->
            try {
                if (task != null) {
                    val inputStream = context.contentResolver.openInputStream(task)
                    val file = File(context.getCacheDir().path, task.encodedPath)
                    FileUtils.copyInputStreamToFile(inputStream, file);
                    coroutineScope.launch {
                        if (file.isFile) {
                            settingsViewModel.imgFile.value = file
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SettingsView(
            onClick = {
                openFileLauncher.launch(Arrays.asList("image/jpeg", "image/png").toTypedArray())
            },
            onClick2 = {
                coroutineScope.launch {
                    settingsViewModel.updateUser(settingsViewModel.imgFile.value)
                }
                Toast.makeText(
                    context,
                    "Oppdatert bruker",
                    Toast.LENGTH_LONG
                ).show()
            },
            imgFile = settingsViewModel.imgFile,
            user = settingsViewModel.loggedInUser,
            textStateFirstName = settingsViewModel.firstName,
            textStateLastName = settingsViewModel.lastName
        )

    }
}

@ExperimentalUnitApi
@ExperimentalCoilApi
@Composable
fun SettingsView(
    onClick: () -> Unit,
    onClick2: () -> Unit,
    imgFile: MutableState<File>,
    user: MutableState<User>,
    textStateFirstName: MutableState<TextFieldValue>,
    textStateLastName: MutableState<TextFieldValue>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color(0xFF005B97))
            .height(200.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(modifier = Modifier.padding(0.dp, 16.dp, 0.dp, 16.dp)) {
            Image(
                painter = if (imgFile.value.isFile) {
                    rememberImagePainter(imgFile.value, builder = {
                        scale(Scale.FILL)
                        transformations(CircleCropTransformation())
                        error(R.drawable.ic_profile)
                    })
                } else {
                    rememberImagePainter(
                        APIUtils.s3LinkParser(user.value.imgKey),
                        builder = {
                            scale(Scale.FILL)
                            transformations(CircleCropTransformation())
                            placeholder(R.drawable.ic_profile)
                            error(R.drawable.ic_profile)
                        })
                },
                contentDescription = "profile image",
                modifier = Modifier
                    .height(200.dp)
                    .width(200.dp)
            )
            Column(modifier = Modifier
                .align(Alignment.BottomEnd)
                .clip(RoundedCornerShape(100.dp))
                .background(Color(0xFF00BCD4))
                .height(50.dp)
                .width(50.dp)
                .clickable(onClick = onClick)
                ,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(6.dp)){
                    Icon(
                        Icons.Filled.Edit,
                        null,
                        modifier = Modifier
                            .size(30.dp)
                            .align(Alignment.Center),
                        tint = Color(0xFFFDFDFD))
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Fornavn", fontSize = TextUnit(value = 4F, type = TextUnitType.Em))
        TextField(
            value = textStateFirstName.value,
            onValueChange = { textStateFirstName.value = it },
            placeholder = { Text(text = user.value.firstName) },
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor =  Color.Transparent, )
        )
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Etternavn", fontSize = TextUnit(value = 4F, type = TextUnitType.Em))
        TextField(
            value = textStateLastName.value,
            onValueChange = { textStateLastName.value = it },
            placeholder = { Text(text = user.value.lastName) },
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor =  Color.Transparent, )
        )
    }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Center
    ) {
        Button(onClick = onClick2, modifier = Modifier.width(260.dp), colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF00BCD4))) {
            Text(text = "Lagre", fontSize = TextUnit(value = 6F, type = TextUnitType.Em))
        }
    }
}

