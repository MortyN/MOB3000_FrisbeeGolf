package no.usn.mob3000_disky.ui.screens.feed.myprofile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.ButtonDefaults.textButtonColors
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import coil.size.Scale
import coil.transform.CircleCropTransformation
import no.usn.mob3000_disky.R
import no.usn.mob3000_disky.api.APIUtils
import no.usn.mob3000_disky.model.*
import no.usn.mob3000_disky.ui.Utils
import no.usn.mob3000_disky.ui.Utils.Companion.getTimeAgo
import no.usn.mob3000_disky.ui.screens.feed.ProfileViewModel

@Composable
fun MyProfile(
    mainViewModel: ProfileViewModel,
    loggedInUser: User
) {

    val results = mainViewModel.postList.value

    val filter = PostFilter(loggedInUser, false, false)
    val previousFilter = mainViewModel.postFilter.value;
    val loading = mainViewModel.loading.value


        LaunchedEffect(key1 = Unit) {
            if(previousFilter.user.userId != filter.user.userId || previousFilter.getFromConnections !== filter.getFromConnections)
            {
                mainViewModel.getPosts(filter)
            }
        }

    val textState = remember { mutableStateOf(TextFieldValue()) }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .height(200.dp)
                .background(color = Color(0xFF005B97))
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Image(
                painter = rememberImagePainter(
                    APIUtils.s3LinkParser(loggedInUser.imgKey),
                    builder = {
                        scale(coil.size.Scale.FILL)
                        transformations(CircleCropTransformation())
                    }
                ),
                contentDescription = "",
                modifier = Modifier
                    .height(170.dp)
                    .width(170.dp)
                    .padding(0.dp, 16.dp, 0.dp, 0.dp)
                    .weight(0.2f)
            )
            Text(
                modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 16.dp),
                text = "${loggedInUser.firstName} ${loggedInUser.lastName}",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = colors.background
            )
        }
        Row(
            modifier = Modifier
                .height(65.dp)
                .padding(0.dp, 10.dp, 0.dp, 0.dp)
        ) {
            TextField(
                value = textState.value,
                onValueChange = { textState.value = it },
                modifier = Modifier.fillMaxHeight(),
                label = { Text("Hva skjer idag?") },
                leadingIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Filled.Edit, contentDescription = "edit")
                    }
                }
            )
            Button(
                onClick = {
                    mainViewModel.createPost(
                        Post(
                            null, loggedInUser,
                            textState.value.text,
                            1,
                            null,
                            "",
                            "",
                            Interactions(),
                            null
                        )
                    )
                }, modifier = Modifier
                    .width(80.dp)
                    .padding(16.dp, 0.dp, 0.dp, 0.dp)
                    .fillMaxHeight(), colors = textButtonColors(
                    backgroundColor = Color(0xFF06B272)
                )
            ) {
                Icon(
                    Icons.Filled.Send,
                    null,
                    modifier = Modifier.fillMaxWidth(),
                    tint = Color.White
                )
            }
        }
        if(!results.isNullOrEmpty() && !loading){
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(results) { p ->
                    PostListItem(
                        post = p,
                        0,
                        0,
                        mainViewModel,
                        loggedInUser)
                }
            }
        } else if(loading){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
                    .padding(20.dp), horizontalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(color = Color(0xFF005B97))
            }
        } else if(results.isNullOrEmpty()){
            Text(
                text = "Du har ingen innlegg for øyeblikket.",
                modifier = Modifier.padding(top = 10.dp),
                color = Color(0xFF777777),
                fontStyle = FontStyle.Italic
            )
        }

    }
}

@Composable
fun PostListItem(
    post: Post,
    index: Int,
    selectedIndex: Int,
    mainViewModel: ProfileViewModel?,
    loggedInUser: User
) {

    var likes by remember {
        mutableStateOf(post.interactions.interactions?.size)
    }

    var likedByUser by remember {
        mutableStateOf(post.interactions.likedByUser)
    }
    val openDialog = remember { mutableStateOf(false) }
    val padding = 16.dp
    Card(
        elevation = 4.dp,
        modifier = Modifier
            .padding(16.dp, 16.dp)
    ) {
        Column(
            Modifier
                .clickable(onClick = { print("HEI") })
                .padding(padding)

        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                //add default image
                Box() {
                    Image(
                        painter = if (post.user.imgKey != null) {
                            rememberImagePainter(APIUtils.s3LinkParser(post.user.imgKey),
                                builder = {
                                    scale(Scale.FILL)
                                    transformations(CircleCropTransformation())
                                })
                        } else {
                            painterResource(R.drawable.logo)
                        },
                        contentDescription = post.message,
                        modifier = Modifier
                            .size(60.dp),
                    )
                }

                Column(Modifier.padding(padding)) {
                    Text(post.user.firstName + " " + post.user.lastName)
                    Text(Utils.getDate(post.postedTs).getTimeAgo())
                }

            }
            Column() {
                Text(text = post.message)
            }


            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()) {
                Row() {
                    IconButton(
                        onClick = {
                            openDialog.value = true
                        },
                    ) {
                        Icon(
                            Icons.Outlined.Clear,
                            contentDescription = "bæsj"
                        )
                    }
                }
                Row(verticalAlignment = Alignment.CenterVertically){
                    IconButton(
                        onClick = {
                            mainViewModel?.interactPost(
                                Interaction(
                                    post = post,
                                    user = loggedInUser,
                                    type = 1
                                )
                            )
                            likes = if(likedByUser){
                                likes?.minus(1)
                            }else{
                                likes?.plus(1)
                            }
                            likedByUser = !likedByUser
                        }
                    ) {
                        Icon(imageVector = if(likedByUser){Icons.Outlined.Favorite}else{Icons.Outlined.FavoriteBorder}, contentDescription = "bæsj")
                    }
                    Text(text = likes.toString(), Modifier.padding(2.dp))
                }
            }
        }
    }
    if(openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            title = {
                Text(text = "Denne operasjonen kan ikke reverseres")
            },
            text = {
                Text("Er du sikker på at du vil slette? ")
            },
            buttons = {
                Row(
                    modifier = Modifier.padding(all = 8.dp).fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = { openDialog.value = false }
                    ) {
                        Text("Tilbake")
                    }
                    Button(
                        onClick = {
                            post.postId?.let {
                                if (mainViewModel != null) {
                                    mainViewModel.deletePost(it)
                                }
                            }
                            openDialog.value = false
                        }
                    ) {
                        Text("Slett")

                    }
                }
            }
        )
    }


}

@Preview(showBackground = true)
@Composable
fun PostFeedListItemPreview() {

    val loggedInUser = User(
        userId = 110,
        userName = "hakonopheim9912212",
        firstName = "Håkon",
        lastName = "Miehpo",
        phoneNumber = "+4741527570",
        password = "***********",
        imgKey = null,
        userLinks = ArrayList()
    )

    val post = Post(
        postId = 101,
        user = loggedInUser,
        message = """
    Vær så snill å rydd opp søppla etter dere.
    Vi har nå hatt dugnad og plukket 3 søppelsekker med søppel.
    Hvis vi skal fortsette å få lov til å ha kurvene der, må vi bli
    flinkere på dette.
        """.trimIndent(),
        postedTs = "grij",
        scoreCard = null,
        type = 2,
        updatedTs = "rgrg",
        interactions = Interactions(),
        sortDate = null
    )

    PostListItem(
        post,
        0,
        0,
        null,
        loggedInUser
    )
}
