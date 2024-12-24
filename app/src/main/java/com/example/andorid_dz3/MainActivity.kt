package com.example.andorid_dz3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.andorid_dz3.ui.theme.Andoriddz3Theme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Andoriddz3Theme {
                MainScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(navController, drawerState)
        }
    ) {
        Scaffold(
            topBar = {
                val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route ?: "Главная"
                val routeMap = mapOf(
                    "home" to "Главная",
                    "doctors" to "Врачи",
                    "services" to "Услуги",
                    "clinics" to "Клиники",
                    "profile" to "Профиль"
                )

                val routeTitle = routeMap[currentRoute] ?: currentRoute

                TopAppBar(
                    title = {
                        Text(
                            text = routeTitle,
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { toggleDrawer(drawerState, coroutineScope) }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    },
                    actions = {
                        IconButton(onClick = { navController.navigate("profile") }) {
                            Icon(Icons.Default.AccountCircle, contentDescription = "Profile")
                        }
                    }
                )
            },
            content = { padding ->
                NavHost(
                    navController = navController,
                    startDestination = "home",
                    modifier = Modifier.padding(padding)
                ) {
                    composable("home") { HomeScreen() }
                    composable("doctors") { DoctorsScreen() }
                    composable("services") { ServicesScreen() }
                    composable("clinics") { ClinicsScreen() }
                    composable("profile") { ProfileScreen() }
                }
            }
        )
    }
}

@Composable
fun DrawerContent(navController: NavController, drawerState: DrawerState) {
    val coroutineScope = rememberCoroutineScope()
    val menuItems = listOf(
        "Главная" to "home",
        "Врачи" to "doctors",
        "Услуги" to "services",
        "Клиники" to "clinics"
    )

    Column(
        modifier = Modifier
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text(
            text = "Навигация",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(16.dp)
        )
        HorizontalDivider()
        menuItems.forEach { (title, route) ->
            ListItem(
                headlineContent = { Text(text = title) },
                modifier = Modifier.clickable {
                    coroutineScope.launch {
                        drawerState.close()
                        navController.navigate(route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun CenteredContent(title: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = title, style = MaterialTheme.typography.titleLarge)
    }
}

fun toggleDrawer(drawerState: DrawerState, coroutineScope: CoroutineScope) {
    coroutineScope.launch {
        if (drawerState.isOpen) {
            drawerState.close()
        } else {
            drawerState.open()
        }
    }
}

@Composable
fun ModalNavigationDrawer(
    drawerState: DrawerState,
    drawerContent: @Composable () -> Unit,
    content: @Composable (paddingValues: PaddingValues) -> Unit
) {
    Box {
        content(PaddingValues(0.dp))
        AnimatedVisibility(
            visible = drawerState.isOpen,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                drawerContent()
            }
        }
    }
}
