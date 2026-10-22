package com.example.utsmobileaplikasion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.utsmobileaplikasion.ui.screens.*
import com.example.utsmobileaplikasion.ui.theme.UtsMobileAplikasionTheme
import com.example.utsmobileaplikasion.viewmodel.FinMatesViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UtsMobileAplikasionTheme {
                FinMatesApp()
            }
        }
    }
}

@Composable
fun FinMatesApp() {
    val navController = rememberNavController()
    val viewModel: FinMatesViewModel = viewModel()
    val snackbarHostState = remember { SnackbarHostState() }

    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route ?: Screen.Splash.route

    // Show push notification popup if triggered
    val notificationMsg = viewModel.lastNotificationMessage
    LaunchedEffect(notificationMsg) {
        if (notificationMsg != null) {
            snackbarHostState.showSnackbar(notificationMsg)
            viewModel.clearNotification()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavHost(
                navController = navController,
                startDestination = Screen.Splash.route
            ) {
                // Layer 1: Splash & Onboarding
                composable(Screen.Splash.route) {
                    SplashOnboardingScreen(
                        onStartClicked = {
                            navController.navigate(Screen.Login.route) {
                                popUpTo(Screen.Splash.route) { inclusive = true }
                            }
                        }
                    )
                }

                // Layer 2: Login
                composable(Screen.Login.route) {
                    LoginScreen(
                        viewModel = viewModel,
                        onLoginSuccess = {
                            navController.navigate(Screen.Beranda.route) {
                                popUpTo(Screen.Login.route) { inclusive = true }
                            }
                        },
                        onNavigateToRegister = {
                            navController.navigate(Screen.Register.route)
                        }
                    )
                }

                // Layer 3: Register
                composable(Screen.Register.route) {
                    RegisterScreen(
                        viewModel = viewModel,
                        onRegisterSuccess = {
                            navController.navigate(Screen.Beranda.route) {
                                popUpTo(Screen.Login.route) { inclusive = true }
                            }
                        }
                    )
                }

                // Layer 4: Beranda
                composable(Screen.Beranda.route) {
                    BerandaScreen(
                        viewModel = viewModel,
                        onNavigateToAdd = {
                            navController.navigate(Screen.TambahTransaksi.route)
                        },
                        onNavigateToDetail = { id ->
                            navController.navigate(Screen.DetailTransaksi.createRoute(id))
                        },
                        onNavigate = { route ->
                            navController.navigate(route) {
                                popUpTo(Screen.Beranda.route) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        currentRoute = currentRoute
                    )
                }

                // Layer 5: Tambah Transaksi
                composable(Screen.TambahTransaksi.route) {
                    TambahTransaksiScreen(
                        viewModel = viewModel,
                        onSaveSuccess = {
                            navController.popBackStack()
                        },
                        onBack = {
                            navController.popBackStack()
                        }
                    )
                }

                // Layer 6: Riwayat Transaksi
                composable(Screen.Riwayat.route) {
                    RiwayatTransaksiScreen(
                        viewModel = viewModel,
                        onNavigateToAdd = {
                            navController.navigate(Screen.TambahTransaksi.route)
                        },
                        onNavigateToDetail = { id ->
                            navController.navigate(Screen.DetailTransaksi.createRoute(id))
                        },
                        onNavigate = { route ->
                            navController.navigate(route) {
                                popUpTo(Screen.Beranda.route) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        currentRoute = currentRoute
                    )
                }

                // Layer 7: Detail Transaksi (with Argument)
                composable(
                    route = Screen.DetailTransaksi.route,
                    arguments = listOf(navArgument("id") { type = NavType.StringType })
                ) { backStackEntry ->
                    val id = backStackEntry.arguments?.getString("id") ?: "1"
                    DetailTransaksiScreen(
                        viewModel = viewModel,
                        transactionId = id,
                        onBack = { navController.popBackStack() },
                        onDeleted = { navController.popBackStack() }
                    )
                }

                // Layer 8: Budget
                composable(Screen.Budget.route) {
                    BudgetScreen(
                        viewModel = viewModel,
                        onNavigate = { route ->
                            navController.navigate(route) {
                                popUpTo(Screen.Beranda.route) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        currentRoute = currentRoute
                    )
                }

                // Layer 9: Mates & Split Bill
                composable(Screen.Mates.route) {
                    MatesScreen(
                        viewModel = viewModel,
                        onNavigateToSplitBill = {
                            navController.navigate(Screen.BuatSplitBill.route)
                        },
                        onNavigate = { route ->
                            navController.navigate(route) {
                                popUpTo(Screen.Beranda.route) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        currentRoute = currentRoute
                    )
                }

                // Layer 10: Buat Split Bill
                composable(Screen.BuatSplitBill.route) {
                    BuatSplitBillScreen(
                        viewModel = viewModel,
                        onSplitSuccess = {
                            navController.popBackStack()
                        },
                        onBack = {
                            navController.popBackStack()
                        }
                    )
                }

                // Layer 11: Profil / Pengaturan & Keamanan
                composable(Screen.Profil.route) {
                    ProfilScreen(
                        viewModel = viewModel,
                        onLogout = {
                            navController.navigate(Screen.Login.route) {
                                popUpTo(0) { inclusive = true }
                            }
                        },
                        onNavigate = { route ->
                            navController.navigate(route) {
                                popUpTo(Screen.Beranda.route) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        currentRoute = currentRoute
                    )
                }
            }
        }
    }
}
