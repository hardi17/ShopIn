package com.hardi.shopin.ui.Screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import com.hardi.shopin.R

@Composable
fun ShopInMainScreen(
    modifier: Modifier
) {

    var selectedIndex by rememberSaveable {
        mutableStateOf(0)
    }

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = colorResource(R.color.light_yellow)
            ) {
                bottomBarItems.forEachIndexed { index, bottomBarItems ->
                    NavigationBarItem(
                        selected = index == selectedIndex,
                        onClick = {
                            selectedIndex = index
                        },
                        label = {
                            Text(
                                text = bottomBarItems.label,
                                color = Color.Black
                            )
                        },
                        icon = {
                            Icon(
                                tint = Color.Black,
                                imageVector = bottomBarItems.icon,
                                contentDescription = bottomBarItems.label
                            )
                        }
                    )
                }
            }
        }
    ) {
        ContentScreen(modifier = modifier.padding(it), selectedIndex)
    }

}

@Composable
fun ContentScreen(modifier: Modifier, selectedIndex: Int) {
    when (selectedIndex) {
        0 -> HomeScreen(modifier)
        1 -> FavoriteScreen(modifier)
        2 -> CartScreen(modifier)
        3 -> ProfileScreen(modifier)
    }
}

data class BottomBarItems(
    val label: String,
    val icon: ImageVector
)

val bottomBarItems = listOf(
    BottomBarItems(
        label = "Home",
        icon = Icons.Default.Home
    ),
    BottomBarItems(
        label = "Favorite",
        icon = Icons.Default.Favorite
    ),
    BottomBarItems(
        label = "Cart",
        icon = Icons.Default.ShoppingCart
    ),
    BottomBarItems(
        label = "Profile",
        icon = Icons.Default.Person
    )
)

