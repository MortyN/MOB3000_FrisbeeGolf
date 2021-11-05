package no.usn.mob3000_disky.ui.screens.feed

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import coil.size.Scale
import coil.transform.CircleCropTransformation
import no.usn.mob3000_disky.R
import no.usn.mob3000_disky.api.APIUtils
import no.usn.mob3000_disky.model.Post
import no.usn.mob3000_disky.model.User
import no.usn.mob3000_disky.ui.screens.myprofile.MyProfileViewModel
import no.usn.mob3000_disky.ui.screens.myprofile.PostListItem
import no.usn.mob3000_disky.ui.theme.HeaderBlue

@Preview(showBackground = true)
@Composable
fun FeedPreview() {
    Column() {
        Text(text = "hei")
    }
}

@Composable
fun Feed(loggedInUser: User, mainViewModel: FeedViewModel) {

    val textState = remember { mutableStateOf(TextFieldValue()) }
    val results = mainViewModel.feedList.value
    val loading = mainViewModel.loading.value

    if(results.isEmpty() && !loading){
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
                PostFeedListItem(post = p, 0, 0){i -> print("CLICKED: $i")}
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun PostFeedListItemPreview(){

    val loggedInUser = User(
        userId = 110,
        userName = "hakonopheim9912212",
        firstName = "Håkon",
        lastName = "Miehpo",
        phoneNumber = "+4741527570",
        password = "***********",
        imgKey = "763c6pojd20mgm54m4j4fctkkp",
        userLinks = null,
        getFromConnections = true,
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
        updatedTs = "rgrg"

    )

    PostFeedListItem(post, 0, 0, onClick = { print("hei")})
}

@Composable
fun PostFeedListItem(post: Post, index: Int, selectedIndex: Int,
                 onClick: (Int) -> Unit
) {
    val backgroundColor =
        if (index == selectedIndex) MaterialTheme.colors.background else MaterialTheme.colors.background

    val padding = 16.dp
    Column(
        Modifier
            .clickable(onClick = { print("HEI") })
            .padding(padding)
            .fillMaxWidth()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            //add default image
            Image(painterResource(id = R.drawable.logo), contentDescription = "hei", modifier = Modifier.requiredSize(70.dp))
            Column {
                Text("Alfred Sisley")
                Text("3 minutes ago")
            }
        }
        Spacer(Modifier.size(padding))
        Card(elevation = 4.dp) {
            Text(text = post.message)
        }
    }
}

