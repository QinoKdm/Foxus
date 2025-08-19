package com.okdm.foxus

import FocusPage
import android.R.attr.title
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.okdm.foxus.ui.theme.FoxusTheme
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontVariation
import com.okdm.foxus.R
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
                var selectedItemIndex by remember { mutableIntStateOf(0) }
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(
                                    text = items[selectedItemIndex],
                                    style = TextStyle(
                                        fontFamily = RobotoFlexVariableTitle,
                                        fontSize = 50.sp
                                    ),
                                    modifier = Modifier.padding(20.dp)
                                )
                            }
                        )
                    },

                    bottomBar = {
                        BottomNavigationBar(
                            selectedItemIndex = selectedItemIndex,
                            onItemSelected = { index -> selectedItemIndex = index }
                        )
                    }
                ){
                    paddingValues ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                    ) {
                        when (selectedItemIndex) {
                            0 -> FocusPage()
                            1 -> TasksPage()
                            2 -> ProfilePage()
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Override
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