package no.usn.mob3000_disky.ui.screens.feed

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import coil.size.Scale
import coil.transform.CircleCropTransformation
import com.google.gson.Gson
import no.usn.mob3000_disky.R
import no.usn.mob3000_disky.api.APIUtils
import no.usn.mob3000_disky.model.*
import no.usn.mob3000_disky.ui.RootNavItem
import no.usn.mob3000_disky.ui.Utils
import no.usn.mob3000_disky.ui.Utils.Companion.getTimeAgo
import no.usn.mob3000_disky.ui.screens.myrounds.ScoreCardResultTable
import kotlin.collections.ArrayList

@Composable
fun Feed(loggedInUser: User, mainViewModel: ProfileViewModel, navController: NavHostController) {

    val results = mainViewModel.postList.value
    val loading = mainViewModel.loading.value

    val filter = PostFilter(loggedInUser, true, false)
    val previousFilter = mainViewModel.postFilter.value;

    LaunchedEffect(key1 = Unit) {
        if(previousFilter.user.userId != filter.user.userId
            || previousFilter.getFromConnections !== filter.getFromConnections
            || previousFilter.getUserLinks !== filter.getUserLinks)
        {
            mainViewModel.getPosts(filter)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if(!results.isNullOrEmpty() && !loading){
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
                        loggedInUser,
                        navController
                    )
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
                text = "Det er ingen innlegg å vise her enda.",
                modifier = Modifier.padding(top = 10.dp),
                color = Color(0xFF777777),
                fontStyle = FontStyle.Italic
            )
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
        imgKey = "",
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
        scoreCard = ScoreCard(),
        type = 2,
        updatedTs = "rgrg",
        interactions = Interactions(),
        sortDate = null
    )
    val navController = rememberNavController()

    PostFeedListItem(post, 0, 0, onClick = { print("hei") }, null, loggedInUser, navController)
}

@Composable
fun PostFeedListItem(
    post: Post, index: Int, selectedIndex: Int,
    onClick: (Int) -> Unit,
    mainViewModel: ProfileViewModel?,
    loggedInUser: User,
    navController: NavHostController
) {
    var likes by remember {
        mutableStateOf(post.interactions.interactions?.size)
    }

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
                        painter =
                            rememberImagePainter(
                                APIUtils.s3LinkParser(post.user.imgKey),
                                builder = {
                                    scale(Scale.FILL)
                                    transformations(CircleCropTransformation())
                                    crossfade(true)
                                    placeholder(R.drawable.ic_profile)
                                    error(R.drawable.ic_profile)
                                }),
                        contentDescription = post.message,
                        modifier = Modifier
                            .size(60.dp)
                            .clickable(
                                enabled = true,
                                onClick = {
                                    if (post.user.userId != loggedInUser.userId) {
                                        val userJson = Gson().toJson(post.user)
                                        navController.navigate(RootNavItem.Profile.route.plus("/$userJson"))
                                    } else {
                                        navController.navigate(RootNavItem.MyProfile.route)
                                    }
                                }
                            )

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

            if(post.type == 2 && post.scoreCard != null){
                ScoreCardResultTable(post.scoreCard, 5.dp)
            }
            Column(horizontalAlignment = Alignment.End, modifier = Modifier.fillMaxWidth()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = {

                        print(post)
                    }) {
                        IconButton(
                            onClick = {
                                mainViewModel?.interactPost(Interaction(
                                    post = post,
                                    user = loggedInUser,
                                    type = 1
                                ))

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

@Composable
fun CircularIndterminateProgressBar(
    isDisplayed: Boolean,
) {
    if (isDisplayed) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .padding(20.dp), horizontalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(color = Color(0xFF005B97))
        }
    }
}

