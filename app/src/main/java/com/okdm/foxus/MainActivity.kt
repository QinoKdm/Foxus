package com.okdm.foxus

import FocusPage
import Focusing
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.okdm.foxus.ui.theme.FoxusTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.okdm.foxus.ui.theme.RobotoFlexVariableTitle

val items = listOf("Focus", "Tasks", "Profile")
val selectedIcons = listOf(Icons.Filled.Home, Icons.Filled.CheckCircle, Icons.Filled.AccountCircle)
val unSelectedIcons = listOf(Icons.Outlined.Home, Icons.Outlined.CheckCircle, Icons.Outlined.AccountCircle)

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class, ExperimentalTextApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FoxusTheme {
                FoxusApp()
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class, ExperimentalTextApi::class, ExperimentalSharedTransitionApi::class)
fun FoxusApp() {
    val navController = rememberNavController()
    SharedTransitionLayout {
        NavHost(
            navController = navController,
            startDestination = "Main"
        ) {
            composable("Main") {
                MainWithScaffold(
                    navController = navController,
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this@composable
                )
            }
            composable("Focusing") {
                Focusing(
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this
                )
            }
        }
    }

}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun AnimatedTopBar(title: String) {
    TopAppBar(
        title = {
            AnimatedContent(
                targetState = title,
                transitionSpec = {
                    // 向上滑出 + 向上滑入
                    slideInVertically(
                        initialOffsetY = { -it },
                        animationSpec = tween(300, easing = FastOutSlowInEasing)
                    ) + fadeIn() togetherWith
                            slideOutVertically(
                                targetOffsetY = { -it },
                                animationSpec = tween(300, easing = FastOutSlowInEasing)
                            ) + fadeOut()
                },
                label = "title"
            ) { text ->
                Text(
                    text = text,
                    style = TextStyle(
                        fontFamily = RobotoFlexVariableTitle,
                        fontSize = 50.sp
                    ),
                    modifier = Modifier.padding(20.dp)
                )
            }
        }
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class, ExperimentalTextApi::class,
    ExperimentalSharedTransitionApi::class
)
fun MainWithScaffold(
    navController: NavController,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
){
    var selectedItemIndex by remember { mutableIntStateOf(0) }
    val prevIndex = remember { mutableIntStateOf(0) }

    val direction = remember(selectedItemIndex) {
        if (selectedItemIndex > (prevIndex.value)) {
            AnimatedContentTransitionScope.SlideDirection.Left
        } else {
            AnimatedContentTransitionScope.SlideDirection.Right
        }.also {
            prevIndex.intValue = selectedItemIndex
        }
    }

    Scaffold(
        topBar = {
            AnimatedTopBar(items[selectedItemIndex])
        },

        bottomBar = {
            BottomNavigationBar(
                selectedItemIndex = selectedItemIndex,
                onItemSelected = { index -> selectedItemIndex = index }
            )
        }
    ){ paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            AnimatedContent(
                targetState = selectedItemIndex,
                transitionSpec = {
                    slideIntoContainer(
                        direction,
                        tween(
                            durationMillis = 300,
                            easing = FastOutSlowInEasing
                        )
                    ) togetherWith
                            slideOutOfContainer(
                                direction,
                                tween(
                                    durationMillis = 300,
                                    easing = FastOutSlowInEasing
                                )
                            )
                },
                label = "bottomBarTransition"
            ) {

                    index ->
                SharedTransitionLayout {
                    when (index) {
                        0 -> FocusPage(
                            onStartClick = { navController.navigate("focusing") },
                            sharedTransitionScope = this@SharedTransitionLayout,
                            animatedVisibilityScope = this@AnimatedContent
                        )
                        1 -> TasksPage()
                        2 -> ProfilePage()
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(
    selectedItemIndex: Int,
    onItemSelected: (Int) -> Unit
) {
    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItemIndex == index,
                onClick = { onItemSelected(index) },
                icon = {
                    Crossfade(
                        targetState = selectedItemIndex == index,
                        animationSpec = tween(durationMillis = 200),
                        label = "icon"
                    ) { selected ->
                        Icon(
                            imageVector =
                                if (selected) selectedIcons[index]
                                else unSelectedIcons[index],
                            contentDescription = item,
                        )
                    }
                },
                label = { Text(item) },
            )
        }
    }
}