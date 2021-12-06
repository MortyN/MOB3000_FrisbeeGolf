package no.usn.mob3000_disky.ui.screens.login

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ExperimentalGraphicsApi
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavAction
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import com.google.android.gms.common.api.ApiException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import no.usn.mob3000_disky.MainActivity
import no.usn.mob3000_disky.MainActivityViewModel
import no.usn.mob3000_disky.R
import no.usn.mob3000_disky.model.User
import no.usn.mob3000_disky.ui.theme.Shapes

//https://github.com/hadiyarajesh/ComposeGoogleSignIn/tree/master/app/src/main/java/com/hadiyarajesh/composegoogle
//På grunn av compose brukte jeg litt hjelp av han her.

@ExperimentalUnitApi
@ExperimentalMaterialApi
@Composable
fun SignInButton(
    text: String,
    loadingText: String = "Logger inn...",
    icon: Painter,
    isLoading: Boolean = false,
    shape: Shape = Shapes.large,
    backgroundColor: Color = MaterialTheme.colors.surface,
    progressIndicatorColor: Color = MaterialTheme.colors.primary,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .clickable(
                enabled = !isLoading,
                onClick = onClick
            )
            .padding(top = 12.dp)
            .clip(RoundedCornerShape(10.dp)),
        shape = shape,
        color = backgroundColor
    ) {
        Row(
            modifier = Modifier
                .padding(
                    start = 12.dp,
                    end = 16.dp,
                    top = 12.dp,
                    bottom = 12.dp
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Icon(
                painter = icon,
                contentDescription = "SignInButton",
                tint = Color.Unspecified,
                modifier = Modifier.clip(RoundedCornerShape(50))
            )
            Spacer(modifier = Modifier.width(8.dp))

            Text(text = if (isLoading) loadingText else text, fontSize = TextUnit(5F, TextUnitType.Em))
            if (isLoading) {
                Spacer(modifier = Modifier.width(16.dp))
                CircularProgressIndicator(
                    modifier = Modifier
                        .height(16.dp)
                        .width(16.dp),
                    strokeWidth = 2.dp,
                    color = progressIndicatorColor
                )
            }
        }
    }
}

@ExperimentalUnitApi
@ExperimentalGraphicsApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
@ExperimentalCoroutinesApi
@ExperimentalMaterialApi
@Composable
fun loginScreen(
    authViewModel: AuthViewModel
) {
    val coroutineScope = rememberCoroutineScope()
    var text by remember {mutableStateOf<String?>(null)}
    val user by remember (authViewModel) { authViewModel.loggedInUser }
    val signInRequestCode = 1

    val authResultLauncher =
        rememberLauncherForActivityResult(contract = AuthResultContract()) { task ->
            try {
                val account = task?.getResult(ApiException::class.java)
                if (account == null) {
                    text = "Google login feilet"
                } else {
                    coroutineScope.launch {
                        authViewModel.signIn(
                            idToken = account.idToken
                        )
                    }
                }
            } catch (e: ApiException) {
                text = "Google login feilet"
            }
        }
    Scaffold (backgroundColor = Color(R.color.green) ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(R.color.green)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(painter = painterResource(id = R.drawable.logo), contentDescription = "Disky App")
            loginView(
                errorText = text,
                onClick = {
                    text = null
                    authResultLauncher.launch(signInRequestCode)
                },
                onCLick2 = {
                    text = null
                    authViewModel.signInTestUser(110)
                }
            )
        }
    }
}

@ExperimentalUnitApi
@ExperimentalGraphicsApi
@ExperimentalMaterialApi
@Composable
fun loginView(
    errorText: String?,
    onClick: () -> Unit,
    onCLick2: () -> Unit

) {
    var isLoading by remember {
        mutableStateOf(false)
    }
    var isLoading2 by remember {
        mutableStateOf(false)
    }

    Scaffold (modifier = Modifier.background(Color.Green)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.hsl(84F, 0.53F, 0.50F)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SignInButton(
                text = "Logg inn med Google",
                loadingText = "Logger inn...",
                isLoading = isLoading,
                icon = painterResource(id = R.drawable.googleg_standard_color_18),
                onClick = {
                    isLoading = true
                    onClick()
                }
            )
            SignInButton(
                text = "Logg inn med test bruker",
                loadingText = "Logger inn...",
                isLoading = isLoading2,
                icon = painterResource(id = R.drawable.logosmall),
                onClick = {
                    isLoading2 = true
                    onCLick2()
                }
            )

            errorText?.let {
                isLoading = false
                Spacer(modifier = Modifier.height(30.dp))
                Text(text = it)
            }

        }
    }
}
