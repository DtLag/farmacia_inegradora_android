package com.example.farmacia_inegradora_android.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

enum class SelectedScreen {
    HOME, INVENTORY, ADD_PRODUCT, RECOMMENDATIONS, SALES_REPORT
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    userName: String,
    onLogout: () -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var currentScreen by remember { mutableStateOf(SelectedScreen.HOME) }
    var isLoadingScreen by remember { mutableStateOf(false) }
    
    val primaryBlue = Color(0xFF3B5BA9)
    val lightBackground = Color(0xFFF5F7FA)

    fun navigateTo(screen: SelectedScreen) {
        scope.launch {
            drawerState.close()
            isLoadingScreen = true
            currentScreen = screen
            delay(800)
            isLoadingScreen = false
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.fillMaxWidth(0.85f),
                drawerContainerColor = Color.White
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(primaryBlue)
                        .padding(32.dp)
                ) {
                    Column {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(64.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = userName,
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Administrador",
                            color = Color.White.copy(alpha = 0.7f),
                            fontSize = 14.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))


                DrawerItem(
                    icon = Icons.Default.Home,
                    label = "Inicio",
                    isSelected = currentScreen == SelectedScreen.HOME,
                    onClick = { navigateTo(SelectedScreen.HOME) }
                )
                DrawerItem(
                    icon = Icons.Default.List, // Harold
                    label = "Inventario",
                    isSelected = currentScreen == SelectedScreen.INVENTORY,
                    onClick = { navigateTo(SelectedScreen.INVENTORY) }
                )
                DrawerItem(
                    icon = Icons.Default.Add, // Micky
                    label = "Añadir Productos",
                    isSelected = currentScreen == SelectedScreen.ADD_PRODUCT,
                    onClick = { navigateTo(SelectedScreen.ADD_PRODUCT) }
                )
                DrawerItem(
                    icon = Icons.Default.ThumbUp, // Yoce
                    label = "Surtir (Recomendación)",
                    isSelected = currentScreen == SelectedScreen.RECOMMENDATIONS,
                    onClick = { navigateTo(SelectedScreen.RECOMMENDATIONS) }
                )
                DrawerItem(
                    icon = Icons.Default.DateRange, // Harold
                    label = "Reporte de Ventas",
                    isSelected = currentScreen == SelectedScreen.SALES_REPORT,
                    onClick = { navigateTo(SelectedScreen.SALES_REPORT) }
                )

                HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp, horizontal = 16.dp))


                DrawerItem(
                    icon = Icons.AutoMirrored.Filled.ExitToApp,
                    label = "Cerrar Sesión",
                    color = Color.Red,
                    onClick = {
                        scope.launch {
                            drawerState.close()
                            onLogout()
                        }
                    }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        val title = when(currentScreen) {
                            SelectedScreen.HOME -> "Farmacia Dr. Perez"
                            SelectedScreen.INVENTORY -> "Inventario"
                            SelectedScreen.ADD_PRODUCT -> "Nuevo Producto"
                            SelectedScreen.RECOMMENDATIONS -> "Recomendaciones"
                            SelectedScreen.SALES_REPORT -> "Reporte de Ventas"
                        }
                        Text(title, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu", tint = Color.White)
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = primaryBlue)
                )
            }
        ) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(lightBackground)
            ) {
                if (isLoadingScreen) {

                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            CircularProgressIndicator(color = primaryBlue)
                            Spacer(modifier = Modifier.height(16.dp))
                            Text("Cargando sección...", color = Color.Black, fontSize = 14.sp)
                        }
                    }
                } else {

                    when (currentScreen) {
                        SelectedScreen.HOME -> WelcomeContent(userName, primaryBlue)
                        SelectedScreen.INVENTORY -> InventoryScreen()
                        SelectedScreen.ADD_PRODUCT -> PlaceholderContent("Añadir Productos (Micky)")
                        SelectedScreen.RECOMMENDATIONS -> PlaceholderContent("Recomendaciones (Yoce)")
                        SelectedScreen.SALES_REPORT -> SalesScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun WelcomeContent(userName: String, primaryBlue: Color) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Bienvenido, $userName", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Selecciona una opción del menú lateral para gestionar la farmacia.", color = Color.Black.copy(alpha = 0.6f), fontSize = 16.sp)
        Spacer(modifier = Modifier.height(48.dp))
        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = null,
            modifier = Modifier.size(120.dp).graphicsLayer(alpha = 0.1f),
            tint = primaryBlue
        )
    }
}

@Composable
fun PlaceholderContent(title: String) {
    Box(modifier = Modifier.fillMaxSize().padding(24.dp), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(imageVector = Icons.Default.Build, contentDescription = null, modifier = Modifier.size(80.dp), tint = Color.LightGray)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = title, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Text(text = "Esta sección estará disponible pronto.", color = Color.Black.copy(alpha = 0.6f))
        }
    }
}

@Composable
fun DrawerItem(
    icon: ImageVector,
    label: String,
    isSelected: Boolean = false,
    color: Color = Color.Black,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) Color(0xFF3B5BA9).copy(alpha = 0.1f) else Color.Transparent
    val contentColor = if (color == Color.Red) color else if (isSelected) Color(0xFF3B5BA9) else Color.Black

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 4.dp)
            .background(backgroundColor, shape = RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = contentColor,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = label,
            fontSize = 16.sp,
            color = contentColor,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
        )
    }
}
