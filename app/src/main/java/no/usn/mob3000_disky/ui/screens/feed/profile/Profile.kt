package no.usn.mob3000_disky.ui.screens.feed.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import coil.size.Scale
import coil.transform.CircleCropTransformation
import no.usn.mob3000_disky.R
import no.usn.mob3000_disky.api.APIUtils
import no.usn.mob3000_disky.model.*
import no.usn.mob3000_disky.ui.Utils
import no.usn.mob3000_disky.ui.Utils.Companion.getTimeAgo
import no.usn.mob3000_disky.ui.screens.feed.ProfileViewModel
import no.usn.mob3000_disky.ui.screens.myrounds.ScoreCardResultTable

@Composable
fun Profile(
    navController: NavHostController,
    mainViewModel: ProfileViewModel,
    loggedInUser: User,
    profileUser: User
) {

    val results = mainViewModel.postList.value
    val filter = PostFilter(profileUser, false, true)
    val previousFilter = mainViewModel.postFilter.value
    val loading = mainViewModel.loading.value



    if(!loading && ( results.isEmpty()
                    || previousFilter.user.userId != filter.user.userId
                    || previousFilter.getFromConnections !== filter.getFromConnections
                    || previousFilter.getUserLinks !== filter.getUserLinks
                )
    ){
        mainViewModel.getPosts(filter)
    }

    var connectionType by remember {
        mutableStateOf(mainViewModel.isFriends(profileUser, loggedInUser))
    }

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
            Box(){
                Image(
                    painter = rememberImagePainter(
                        APIUtils.s3LinkParser(profileUser.imgKey),
                        builder = {
                            transformations(CircleCropTransformation())
                            scale(Scale.FILL)
                            crossfade(true)
                            placeholder(R.drawable.ic_profile)
                            error(R.drawable.ic_profile)
                        }
                    ),
                    contentDescription = "Hmm",
                    modifier = Modifier
                        .height(140.dp)
                        .width(150.dp)
                        .padding(0.dp, 16.dp, 0.dp, 0.dp)
                )
                Column(modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .clip(RoundedCornerShape(100.dp))
                    .background(Color(0xFF00BCD4))
                    .height(50.dp)
                    .width(50.dp)
                    .clickable {
                        mainViewModel.onFriendIconClicked(loggedInUser, profileUser)
                        when (connectionType) {
                            UserLink.USER_LINK_TYPE_ACCEPTED -> connectionType =
                                UserLink.USER_LINK_TYPE_NO_CONNECTION
                            UserLink.USER_LINK_TYPE_PENDING -> connectionType =
                                UserLink.USER_LINK_TYPE_NO_CONNECTION
                            UserLink.USER_LINK_TYPE_NO_CONNECTION -> connectionType =
                                UserLink.USER_LINK_TYPE_PENDING
                        }

                    },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                        Icon(
                            when(connectionType){
                                UserLink.USER_LINK_TYPE_NO_CONNECTION -> Icons.Filled.PersonAdd
                                UserLink.USER_LINK_TYPE_ACCEPTED -> Icons.Filled.PeopleAlt
                                UserLink.USER_LINK_TYPE_PENDING ->  Icons.Filled.PersonAddDisabled
                                else -> Icons.Filled.ReportOff
                            },
                            null,
                            modifier = Modifier
                                .size(30.dp),
                            tint = Color(0xFFFDFDFD),
                        )
                    }
            }
            Text(
                modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 16.dp),
                text = "${profileUser.firstName} ${profileUser.lastName}",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = colors.background
            )
        }
        if(!results.isNullOrEmpty() && !loading){
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(results) { p ->
                    PostListItem(post = p, 0, 0, {i -> print("CLICKED: $i")}, mainViewModel, loggedInUser, navController)
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
                text = "${profileUser.firstName} ${profileUser.lastName} har ingen innlegg for øyeblikket.",
                modifier = Modifier.padding(top = 10.dp),
                color = Color(0xFF777777),
                fontStyle = FontStyle.Italic
            )
        }
        
    }
}

@Composable
fun PostListItem(
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
                        painter = if (post.user.imgKey != null) {
                            rememberImagePainter(APIUtils.s3LinkParser(post.user.imgKey),
                                builder = {
                                    scale(Scale.FILL)
                                    transformations(CircleCropTransformation())
                                    placeholder(R.drawable.ic_profile)
                                    error(R.drawable.ic_profile)
                                })
                        } else {
                            painterResource(R.drawable.logo)
                        },
                        contentDescription = post.message,
                        modifier = Modifier
                            .size(60.dp)
                    )
                }

                Column(Modifier.padding(padding)) {
                    Text(post.user.firstName + " " + post.user.lastName)
                    Text(Utils.getTimeSince(post.postedTs))
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

@Preview
@Composable
fun preview(){
    var connectionType by remember {
        mutableStateOf(1)
    }
    Box(){
        Image(
            painter = rememberImagePainter(
                APIUtils.s3LinkParser("profileUser.imgKey"),
                builder = {
                    crossfade(true)
                    placeholder(R.drawable.logo)
                    error(R.drawable.logo)
                    transformations(CircleCropTransformation())
                    scale(Scale.FILL)
                }
            ),
            contentDescription = "Hmm",
            modifier = Modifier
                .height(140.dp)
                .width(150.dp)
                .padding(0.dp, 16.dp, 0.dp, 0.dp)
        )
        Column(modifier = Modifier
            .align(Alignment.BottomEnd)
            .clip(RoundedCornerShape(100.dp))
            .background(Color(0xFF00BCD4))
            .height(50.dp)
            .width(50.dp)
            .clickable {
                //mainViewModel.onFriendIconClicked(loggedInUser, profileUser)
                when (connectionType) {
                    UserLink.USER_LINK_TYPE_ACCEPTED -> connectionType =
                        UserLink.USER_LINK_TYPE_NO_CONNECTION
                    UserLink.USER_LINK_TYPE_PENDING -> connectionType =
                        UserLink.USER_LINK_TYPE_NO_CONNECTION
                    UserLink.USER_LINK_TYPE_NO_CONNECTION -> connectionType =
                        UserLink.USER_LINK_TYPE_PENDING
                }

            },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if(connectionType == UserLink.USER_LINK_TYPE_ACCEPTED || connectionType == UserLink.USER_LINK_TYPE_NO_CONNECTION){
                Icon(
                    when(connectionType){
                        UserLink.USER_LINK_TYPE_NO_CONNECTION -> Icons.Filled.PersonAdd
                        UserLink.USER_LINK_TYPE_ACCEPTED -> Icons.Filled.Person
                        else -> Icons.Filled.Person

                    },
                    null,
                    modifier = Modifier
                        .size(30.dp),
                    tint = Color(0xFFFDFDFD),
                )
            } else{
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(6.dp)){
                    Icon(Icons.Filled.Person,
                        null,
                        modifier = Modifier
                            .size(30.dp)
                            .align(Alignment.BottomEnd),
                        tint = Color(0xFFFDFDFD))
                    Icon(Icons.Outlined.Schedule,
                        null,
                        modifier = Modifier
                            .size(20.dp)
                            .align(Alignment.TopStart)
                        ,
                        tint = Color(0xFFFDFDFD)
                    )
                }
            }
        }

    }
}

