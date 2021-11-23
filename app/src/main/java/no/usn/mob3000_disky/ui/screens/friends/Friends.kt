package no.usn.mob3000_disky.ui.screens.friends

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import coil.size.Scale
import coil.transform.CircleCropTransformation
import com.google.gson.Gson
import no.usn.mob3000_disky.R
import no.usn.mob3000_disky.api.APIUtils
import no.usn.mob3000_disky.model.ScoreCard
import no.usn.mob3000_disky.model.User
import no.usn.mob3000_disky.model.UserLink
import no.usn.mob3000_disky.ui.RootNavItem

@ExperimentalMaterialApi
@Composable
fun Friends(loggedInUser: User, mainViewModel: FriendsViewModel, navController: NavHostController) {
    val friendsList = mainViewModel.friendsList.value
    val pendingFriendList = mainViewModel.pendingFriendList.value
    val userList = mainViewModel.userList.value

    LaunchedEffect(key1 = Unit) {
        mainViewModel.getLists(loggedInUser)
    }
    var selectedTabIndex by remember { mutableStateOf(0) }

Column() {
    TabRow(
        selectedTabIndex = selectedTabIndex,
        modifier = Modifier.fillMaxWidth(),
    ) {
        listOf("Venner", "Forespørsel", "Finn venner").forEachIndexed { i, text ->
            Tab(
                selected = selectedTabIndex == i,
                onClick = { selectedTabIndex = i },
                modifier = Modifier.height(50.dp),
                text = { Text(text) }
            )
        }
    }

    when(selectedTabIndex){
        0 -> FriendList(friendsList, loggedInUser, mainViewModel, navController)
        1 -> PendingList(pendingFriendList, loggedInUser, mainViewModel, navController)
        2 -> UserList(userList, loggedInUser, mainViewModel, navController)
    }
}
}

@ExperimentalMaterialApi
@Composable
fun FriendList(list: List<UserLink>, loggedInUser: User, mainViewModel: FriendsViewModel, navController: NavHostController){
    val searchKeyword = remember { mutableStateOf(TextFieldValue("")) }
    SearchBox(searchKeyword)
    var filteredList = list.filter { link ->  matchFriendListSearchResult(link, loggedInUser, searchKeyword) }
    if(!filteredList.isNullOrEmpty()){
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            modifier = Modifier.fillMaxWidth(),
        ) {
            items(filteredList) { u ->
                listItem(
                    u,
                    loggedInUser,
                    mainViewModel,
                    navController
                )
            }
        }
    } else {
        Text(
            text = "Du har ingen venner enda",
            modifier = Modifier.padding(top = 10.dp).fillMaxWidth(),
            color = Color(0xFF777777),
            fontStyle = FontStyle.Italic,
            textAlign = TextAlign.Center
        )
    }
}

@ExperimentalMaterialApi
@Composable
fun PendingList(list: List<UserLink>, loggedInUser: User, mainViewModel: FriendsViewModel, navController: NavHostController){val searchKeyword = remember { mutableStateOf(TextFieldValue("")) }
    SearchBox(searchKeyword)
    var filteredList = list.filter { link ->  matchFriendListSearchResult(link, loggedInUser, searchKeyword) }
    if(!filteredList.isNullOrEmpty()) {
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(filteredList) { u ->
                listItem(
                    u,
                    loggedInUser,
                    mainViewModel,
                    navController
                )
            }
        }
    } else {
        Text(
            text = "Du har ingen venneforespørsler.",
            modifier = Modifier.padding(top = 10.dp).fillMaxWidth(),
            color = Color(0xFF777777),
            fontStyle = FontStyle.Italic,
            textAlign = TextAlign.Center
        )
    }
}

@ExperimentalMaterialApi
@Composable
fun UserList(list: List<User>, loggedInUser: User, mainViewModel: FriendsViewModel, navController: NavHostController){
    val searchKeyword = remember { mutableStateOf(TextFieldValue("")) }
    SearchBox(searchKeyword)

    var filteredList = list.filter { user ->  matchUserListSearchResult(user, searchKeyword) }
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(filteredList) { u ->

                userListItem(
                    u,
                    loggedInUser,
                    mainViewModel,
                    navController
                )
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun userListItem(user: User, loggedInUser: User, mainViewModel: FriendsViewModel, navController: NavHostController) {

    var userLink by remember {
        mutableStateOf(user.userLinks.find { link -> link.userLink1.userId == loggedInUser.userId })
    }

    Card(
        elevation = 4.dp,
        modifier = Modifier
            .padding(16.dp, 16.dp),
        onClick = {
            val userJson = Gson().toJson(user)
            navController.navigate(RootNavItem.Profile.route.plus("/$userJson"))
        }
    ) {
        Column(
            Modifier
                .clickable(onClick = { print("HEI") })
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                //add default image
                Box() {
                    Image(
                        painter =
                        rememberImagePainter(
                            APIUtils.s3LinkParser(user.imgKey),
                            builder = {
                                scale(Scale.FILL)
                                transformations(CircleCropTransformation())
                                crossfade(true)
                                placeholder(R.drawable.ic_profile)
                                error(R.drawable.ic_profile)
                            }),
                        contentDescription = "ost.message",
                        modifier = Modifier
                            .size(60.dp)
                            .clickable(
                                enabled = true,
                                onClick = {
                                        val userJson = Gson().toJson(user)
                                        navController.navigate(RootNavItem.Profile.route.plus("/$userJson"))
                                }
                            )
                    )
                }

                Column(Modifier.padding(16.dp)) {

                    Row(modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically) {
                        Text("${user.firstName} ${user.lastName}")

                            IconButton(
                                onClick = {
                                            if(userLink != null){
                                                mainViewModel.deleteFriendRequest(loggedInUser,user)
                                                userLink = null
                                            } else {
                                                mainViewModel.addFriend(loggedInUser, user)
                                                userLink = UserLink(loggedInUser, user, 0, UserLink.USER_LINK_TYPE_PENDING)

                                            }
                                          }
                            ) {
                                Icon(
                                    if(userLink != null){
                                        Icons.Filled.PersonAddDisabled
                                    } else {
                                        Icons.Filled.PersonAdd
                                    },
                                    contentDescription = "Localized description"
                                )
                            }
                    }

                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun listItem(userLink: UserLink, loggedInUser: User, mainViewModel: FriendsViewModel, navController: NavHostController){

    val user: User

    if(userLink.userLink1.userId == loggedInUser.userId){
        user = userLink.userLink2
    } else {
        user = userLink.userLink1
    }

    Card(
        elevation = 4.dp,
        modifier = Modifier
            .padding(16.dp, 16.dp)
    ) {
        Column(
            Modifier
                .clickable(onClick = { print("HEI") })
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                //add default image
                Box() {
                    Image(
                        painter =
                        rememberImagePainter(
                            APIUtils.s3LinkParser(user.imgKey),
                            builder = {
                                scale(Scale.FILL)
                                transformations(CircleCropTransformation())
                                crossfade(true)
                                placeholder(R.drawable.ic_profile)
                                error(R.drawable.ic_profile)
                            }),
                        contentDescription = "ost.message",
                        modifier = Modifier
                            .size(60.dp)
                            .clickable(
                                enabled = true,
                                onClick = {
                                        var fullUserObject = mainViewModel.users.value.find { o -> o.userId ==  user.userId}
                                        val userJson = Gson().toJson(fullUserObject)
                                        navController.navigate(RootNavItem.Profile.route.plus("/$userJson"))
                                }
                            )

                    )
                }

                Column(Modifier.padding(16.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            "${user.firstName} ${user.lastName}",
                                modifier = Modifier.weight(0.8f)
                            )

                            IconButton(
                                onClick = {
                                          if(userLink.type == UserLink.USER_LINK_TYPE_ACCEPTED){
                                              // Remove/toggle
                                              mainViewModel.deleteFriend(loggedInUser, user)

                                          } else{
                                              mainViewModel.acceptFriendRequest(userLink)
                                          }
                                },
                                modifier = Modifier.weight(0.1f)) {
                                Icon(
                                    if(userLink.type == UserLink.USER_LINK_TYPE_ACCEPTED) {
                                        Icons.Filled.PersonRemove
                                    } else {
                                        Icons.Filled.HowToReg
                                    },
                                    contentDescription = "Localized description"

                                )
                            }
                            if(userLink.type == UserLink.USER_LINK_TYPE_PENDING){
                                IconButton(
                                    onClick = {
                                                mainViewModel.declineFriendRequest(user, loggedInUser)
                                              },
                                    modifier = Modifier.weight(0.1f)) {
                                    Icon(
                                        Icons.Filled.Close,
                                        contentDescription = "Localized description"
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
}

@Composable
fun SearchBox(state: MutableState<TextFieldValue>) {
    TextField(
        value = state.value,
        onValueChange = { value ->
            state.value = value
        },
        modifier = Modifier
            .fillMaxWidth(),
        textStyle = TextStyle(color = Color.White),
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = "",
                modifier = Modifier
                    .padding(15.dp)
                    .size(24.dp)
            )
        },
        trailingIcon = {
            if (state.value != TextFieldValue("")) {
                IconButton(
                    onClick = {
                        state.value =
                            TextFieldValue("") // Remove text from TextField when you press the 'X' icon
                    }
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "",
                        modifier = Modifier
                            .padding(15.dp)
                            .size(24.dp)
                    )
                }
            }
        },
        singleLine = true,
        shape = RectangleShape, // The TextFiled has rounded corners top left and right by default
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.White,
            cursorColor = Color.White,
            leadingIconColor = Color.White,
            trailingIconColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}

fun matchUserListSearchResult(user: User, searchKeyword: MutableState<TextFieldValue>): Boolean {
    val searchText = searchKeyword.value.text
    if(searchText.isEmpty()) return true
    else if ("${user.firstName} ${user.lastName}".lowercase().contains(searchText.lowercase())){
        return true
    }
    return false
}

fun matchFriendListSearchResult(link: UserLink, loggedInUser: User, searchKeyword: MutableState<TextFieldValue>): Boolean {
    val user: User
    if(link.userLink1.userId == loggedInUser.userId){
        user = link.userLink2
    } else {
        user = link.userLink1
    }
    return matchUserListSearchResult(user, searchKeyword)
}