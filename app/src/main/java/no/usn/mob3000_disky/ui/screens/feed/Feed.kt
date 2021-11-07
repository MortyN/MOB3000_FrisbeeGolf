package no.usn.mob3000_disky.ui.screens.feed

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.size.Scale
import coil.transform.CircleCropTransformation
import no.usn.mob3000_disky.R
import no.usn.mob3000_disky.api.APIUtils
import no.usn.mob3000_disky.model.Interaction
import no.usn.mob3000_disky.model.Post
import no.usn.mob3000_disky.model.User
import no.usn.mob3000_disky.ui.theme.HeaderBlue

@Composable
fun Feed(loggedInUser: User, mainViewModel: FeedViewModel) {

    val textState = remember { mutableStateOf(TextFieldValue()) }
    val results = mainViewModel.feedList.value
    val loading = mainViewModel.loading.value

    if (results.isEmpty() && !loading) {
        mainViewModel.getPosts(loggedInUser)
    }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally
    ) {

        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(results) { p ->
                PostFeedListItem(
                    post = p,
                    0,
                    0,
                    { i -> print("CLICKED: $i") },
                    mainViewModel,
                    loggedInUser
                )
            }

        }
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
        userLinks = null,
        getFromConnections = true,
    )

//    val post = Post(
//        postId = 101,
//        user = loggedInUser,
//        message = """
//    Vær så snill å rydd opp søppla etter dere.
//    Vi har nå hatt dugnad og plukket 3 søppelsekker med søppel.
//    Hvis vi skal fortsette å få lov til å ha kurvene der, må vi bli
//    flinkere på dette.
//        """.trimIndent(),
//        postedTs = "grij",
//        scoreCard = null,
//        type = 2,
//        updatedTs = "rgrg",
//        interactions = ArrayList<Interaction>()
//    )

//    PostFeedListItem(post, 0, 0, onClick = { print("hei") }, null, loggedInUser)
}

@Composable
fun PostFeedListItem(
    post: Post, index: Int, selectedIndex: Int,
    onClick: (Int) -> Unit,
    mainViewModel: FeedViewModel?,
    loggedInUser: User
) {

    var likes by remember {
        mutableStateOf(post.interactions.interactions?.size)
    }

    val backgroundColor =
        if (index == selectedIndex) MaterialTheme.colors.background else MaterialTheme.colors.background


    var likedByUser by remember {
        mutableStateOf(post.interactions.likedByUser)
    }


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
                .fillMaxWidth()
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
                    Text(post.postedTs)
                }
            }
            Column() {
                Text(text = post.message)
            }
            Column(horizontalAlignment = Alignment.End, modifier = Modifier.fillMaxWidth()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = {
                        mainViewModel?.interactPost(
                            Interaction(
                                post = post,
                                user = loggedInUser,
                                type = 1
                            )
                        )
                        print(post)
                    }) {
                        IconButton(
                            onClick = {
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

                    }
                    Text(text = likes.toString(), Modifier.padding(2.dp))
                }
            }
        }
    }

}

