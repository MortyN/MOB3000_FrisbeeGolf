package no.usn.mob3000_disky.ui.screens.settings

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
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

@ExperimentalCoilApi
@Composable
fun SettingsScreen(
    settingsViewModel: SettingsViewModel
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val openFileLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.OpenDocument()) { task ->
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

    Scaffold() {
        Column (modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
            SettingsView(
                onClick = {
                    openFileLauncher.launch(Arrays.asList("image/jpeg", "image/png").toTypedArray())
                },
                onClick2 = {
                    coroutineScope.launch{
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
            )

        }
    }
}

@ExperimentalCoilApi
@Composable
fun SettingsView(
    onClick: () -> Unit,
    onClick2: () -> Unit,
    imgFile: MutableState<File>,
    user: MutableState<User>,
) {
    Card (
        elevation = 4.dp,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                ) {
                    Image(
                        painter = if (imgFile.value.isFile) {
                            rememberImagePainter(imgFile.value)
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
                            .size(240.dp)
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(onClick = onClick) {
                    Text(text = "Last opp profilbilde")
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick = onClick2) {
                Text(text = "Lagre")
            }
        }
    }
}