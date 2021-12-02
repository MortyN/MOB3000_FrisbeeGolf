package no.usn.mob3000_disky.ui.screens.settings

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import org.apache.commons.io.FileUtils
import java.io.File
import java.util.*

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
                val file = File(context.getCacheDir().path, "tempFile")
                FileUtils.copyInputStreamToFile(inputStream, file);
                coroutineScope.launch {
                    if (file.isFile) {
                        settingsViewModel.updateUser(file)
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
                }
            )

        }
    }
}

@Composable
fun SettingsView(
    onClick: () -> Unit
) {
    Scaffold() {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row (
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Button(onClick = onClick) {
                    Text(text = "Last opp profilbildet")
                }
            }
        }
    }

}