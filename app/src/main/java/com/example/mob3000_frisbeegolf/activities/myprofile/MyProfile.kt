package com.example.mob3000_frisbeegolf.activities.myprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.example.mob3000_frisbeegolf.api.model.PostResponse
import com.example.mob3000_frisbeegolf.api.model.UserResponse
import com.example.mob3000_frisbeegolf.api.util.APIUtils

@Composable
fun SimpleComposable(data: List<PostResponse>, user: UserResponse) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Top
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
                painter = rememberImagePainter(APIUtils.s3LinkParser(user.imgKey),
                    builder = {
                        scale(coil.size.Scale.FILL)
                        transformations(CircleCropTransformation())
                    }
                ),
                contentDescription = "",
                modifier = Modifier
                    .height(170.dp)
                    .width(170.dp)
                    .weight(0.2f)
            )
            Text(
                modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 16.dp),
                text = "${user.firstName} ${user.lastName}",
                fontSize = 30.sp,
                color = colors.background
            )
        }
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(items = data) { p ->
                PostListItem(post = p, 0, 0) { i ->
                    print(i)
                }
            }
        }
    }
}

@Composable
fun PostListItem(post: PostResponse, index: Int, selectedIndex: Int, onClick: (Int) -> Unit) {
    val backgroundColor =
        if (index == selectedIndex) MaterialTheme.colors.background else MaterialTheme.colors.background
    Card(
        modifier = Modifier
            .padding(16.dp, 16.dp)
            .wrapContentHeight()
            .clickable { onClick(index) }, shape = RoundedCornerShape(8.dp), elevation = 4.dp
    ) {
        Surface(color = backgroundColor) {

            Row(
                Modifier
                    .padding(16.dp)
                    .wrapContentHeight()
            ) {
                Image(
                    painter = rememberImagePainter(APIUtils.s3LinkParser(post.user.imgKey),
                        builder = {
                            scale(coil.size.Scale.FILL)
                            transformations(CircleCropTransformation())
                        }
                    ),
                    contentDescription = post.message,
                    modifier = Modifier
                        .height(70.dp)
                        .width(70.dp)
                        .weight(0.2f)
                )

                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(4.dp)
                        .wrapContentSize()
                        .weight(0.8f)
                ) {
                    Text(
                        text = "${post.user.firstName} ${post.user.lastName}",
                        style = typography.subtitle1,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = post.message,
                        style = typography.caption,
                        modifier = Modifier
                            .wrapContentSize()
                    )

                }
            }
        }
    }
}

class MyProfile : Fragment() {
    private val mainViewModel by viewModels<MyProfileViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            val user: UserResponse = UserResponse(
                userId = 110,
                userName = "hakonopheim9912212",
                firstName = "Håkon",
                lastName = "Miehpo",
                phoneNumber = "+4741527570",
                password = "***********",
                imgKey = "763c6pojd20mgm54m4j4fctkkp",
                userLinks = null
            )
            setContent {
                SimpleComposable(mainViewModel.feedListResponse, user)
                mainViewModel.getFeedList()
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun Testclass() {
    Column(modifier = Modifier.wrapContentHeight()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                .height(80.dp)
                .fillMaxWidth()
                .background(colors.background)
        ) {
            Text(text = "ieo")
        }
        Column() {
            Text(text = "iefj")
            Text(text = "iefj")
            Text(text = "iefj")
            Text(text = "iefj")
            Text(text = "iefj")
            Text(text = "iefj")
            Text(text = "iefj")
            Text(text = "iefj")
        }
    }
}

