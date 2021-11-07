package no.usn.mob3000_disky

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import no.usn.mob3000_disky.model.User
import no.usn.mob3000_disky.ui.ROOT_ROUTE
import no.usn.mob3000_disky.ui.RootNavItem
import no.usn.mob3000_disky.ui.screens.round.AddRound
import no.usn.mob3000_disky.ui.screens.feed.Feed
import no.usn.mob3000_disky.ui.screens.feed.FeedViewModel
import no.usn.mob3000_disky.ui.screens.myprofile.MyProfile
import no.usn.mob3000_disky.ui.screens.myprofile.MyProfileViewModel
import no.usn.mob3000_disky.ui.screens.round.ChooseTrack
import no.usn.mob3000_disky.ui.screens.round.nav.AddRoundNavItem
import no.usn.mob3000_disky.ui.screens.round.nav.CURRENTROUND_ROUTE
import no.usn.mob3000_disky.ui.screens.round.nav.addRoundNavGraph
import no.usn.mob3000_disky.ui.theme.HeaderBlue
import no.usn.mob3000_disky.ui.theme.SelectedBlue
import no.usn.mob3000_disky.ui.theme.appName
import javax.inject.Inject

// project structure https://stackoverflow.com/questions/68304586/how-to-structure-a-jetpack-compose-project

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var someRandomString: String

    private val myProfileViewModel: MyProfileViewModel by viewModels()
    private val feedViewModel: FeedViewModel by viewModels()

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

    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
            val scope = rememberCoroutineScope()
            val navController = rememberNavController()
            // If you want the drawer from the right side, uncomment the following
            // CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            Scaffold(
                scaffoldState = scaffoldState,
                topBar = { TopBar(scope = scope, scaffoldState = scaffoldState) },
                drawerBackgroundColor = Color(0xFFF5F5F5),
                drawerGesturesEnabled = true,
                // scrimColor = Color.Red,  // Color for the fade background when you open/close the drawer
                drawerContent = {
                    Drawer(scope = scope, scaffoldState = scaffoldState, navController = navController)
                },
                bottomBar = { BottomNavigationBar(navController) }
            ) {
                Navigation(
                    navController = navController,
                    loggedInUser = loggedInUser,
                    scaffoldState = scaffoldState,
                    myProfileViewModel = myProfileViewModel,
                    feedViewModel = feedViewModel,
                    )
            }
            // }
        }
    }

}

@Composable
fun TopBar(scope: CoroutineScope, scaffoldState: ScaffoldState) {
    TopAppBar(
        title = { Text(text = appName, fontSize = 18.sp) },
        navigationIcon = {
            IconButton(onClick = {
                scope.launch {
                    scaffoldState.drawerState.open()
                }
            }) {
                Icon(Icons.Filled.Menu, "")
            }
        },
        backgroundColor = HeaderBlue,
        contentColor = Color.White
    )
}

@Preview(showBackground = false)
@Composable
fun TopBarPreview() {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    TopBar(scope = scope, scaffoldState = scaffoldState)
}

@Composable
fun Drawer(scope: CoroutineScope, scaffoldState: ScaffoldState, navController: NavController) {
    val items = listOf(
        RootNavItem.MyProfile,
        RootNavItem.Friends,
        RootNavItem.TrackRecords,
        RootNavItem.AddArena,
        RootNavItem.MyTracks
    )
    Column(
        modifier = Modifier.padding(16.dp,16.dp,16.dp,0.dp),
        horizontalAlignment = Alignment.Start
    ) {
        // Header
        Row(
            modifier = Modifier.wrapContentHeight(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = R.drawable.logo.toString(),
                modifier = Modifier
                    .height(100.dp)
                    .padding(10.dp)
                    .clip(CircleShape)
            )
            Text(text = "Hei, Petter Stordalen", fontSize = 20.sp, color = Color.Black, fontWeight = FontWeight.Bold)
        }
        // Space between
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp)
        )
        Divider(color = Color.Gray,modifier = Modifier
            .fillMaxWidth()
            .width(2.dp))
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp)
        )
        // List of navigation items
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            DrawerItem(item = item, selected = currentRoute == item.route, onItemClick = {
                navController.navigate(item.route) {
                    // Pop up to the start destination of the graph to
                    // avoid building up a large stack of destinations
                    // on the back stack as users select items
                    navController.graph.startDestinationRoute?.let { route ->
                        popUpTo(route) {
                            saveState = true
                        }
                    }
                    // Avoid multiple copies of the same destination when
                    // reselecting the same item
                    launchSingleTop = true
                    // Restore state when reselecting a previously selected item
                    restoreState = true
                }
                // Close drawer
                scope.launch {
                    scaffoldState.drawerState.close()
                }
            })
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp)
        )
        Divider(color = Color.Gray,modifier = Modifier
            .fillMaxWidth()
            .width(2.dp))
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp)
        )
        DrawerItem(item = RootNavItem.Settings, selected = currentRoute == RootNavItem.Settings.route, onItemClick = {
            navController.navigate(RootNavItem.Settings.route) {
                navController.graph.startDestinationRoute?.let { route ->
                    popUpTo(route) {
                        saveState = true
                    }
                }
                launchSingleTop = true
                restoreState = true
            }
            // Close drawer
            scope.launch {
                scaffoldState.drawerState.close()
            }

        })
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "The frisbee golf app you need",
            color = Color.White,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(12.dp)
                .align(Alignment.CenterHorizontally)
        )
    }
}

@Preview(showBackground = false)
@Composable
fun DrawerPreview() {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val navController = rememberNavController()
    Drawer(scope = scope, scaffoldState = scaffoldState, navController = navController)
}

@Composable
fun DrawerItem(item: RootNavItem, selected: Boolean, onItemClick: (RootNavItem) -> Unit) {
    val background = if (selected) R.color.teal_200 else android.R.color.transparent
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onItemClick(item) })
            .height(45.dp)
            .background(colorResource(id = background))
            .padding(start = 10.dp)
            .clip(CircleShape)
    ) {
        Image(
            imageVector = item.icon,
            contentDescription = item.title,
            colorFilter = ColorFilter.tint(Color.Black),
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .height(35.dp)
                .width(35.dp)
        )
        Spacer(modifier = Modifier.width(7.dp))
        Text(
            text = item.title,
            fontSize = 18.sp,
            color = Color.Black
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DrawerItemPreview() {
    DrawerItem(item = RootNavItem.Feed, selected = true, onItemClick = {})
}

@Composable
fun BottomNavigationBar(navController: NavHostController){
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val items = listOf(
        RootNavItem.Feed,
        RootNavItem.MyRounds,
        RootNavItem.AddRound,
        RootNavItem.MyProfile
    )

    BottomNavigation(
        backgroundColor = Color.White,
        contentColor = Color.White
    ) {
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = { Text(text = item.title) },
                selectedContentColor = SelectedBlue,
                unselectedContentColor = Color.Gray.copy(0.4f),
                alwaysShowLabel = false,
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route){

                        /*
                        using pop up to avoid building up large stack of destinations on users backstack
                         */

                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route){
                                saveState = true
                            }
                        }
                        //Avoid stacking same destination when clicking on same navbarbutton multiple times
                        launchSingleTop = true
                        //restore state when reselecting previous navigation item
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview() {
    //BottomNavigationBar()
}

@ExperimentalAnimationApi
@Composable
fun Navigation(
    navController: NavHostController,
    myProfileViewModel: MyProfileViewModel,
    feedViewModel: FeedViewModel,
    loggedInUser: User,
    scaffoldState: ScaffoldState
) {

    //https://proandroiddev.com/jetpack-compose-navigation-architecture-with-viewmodels-1de467f19e1c

    NavHost(
        navController,
        startDestination = RootNavItem.Feed.route,
        route = ROOT_ROUTE) {

        composable(RootNavItem.Feed.route) {
            Feed(
                loggedInUser,
                feedViewModel
            )
        }
        composable(RootNavItem.MyRounds.route) {
            MusicScreen()
        }
//        composable(RootNavItem.AddRound.route) {
//            scaffoldState.drawerState.isOpen
//            AddRound()
//
//        }
        composable(RootNavItem.MyProfile.route) {
            MyProfile(
                loggedInUser = loggedInUser,
                mainViewModel = myProfileViewModel
            )

        }
        addRoundNavGraph(navController = navController)
    }
}