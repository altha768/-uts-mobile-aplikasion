package com.example.utsmobileaplikasion.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.utsmobileaplikasion.ui.theme.FinGreenPrimary

sealed class Screen(val route: String, val title: String) {
    object Splash : Screen("splash", "Splash")
    object Login : Screen("login", "Masuk")
    object Register : Screen("register", "Daftar")
    object Beranda : Screen("beranda", "Beranda")
    object TambahTransaksi : Screen("tambah_transaksi", "Tambah")
    object Riwayat : Screen("riwayat", "Riwayat")
    object DetailTransaksi : Screen("detail_transaksi/{id}", "Detail") {
        fun createRoute(id: String) = "detail_transaksi/$id"
    }
    object Budget : Screen("budget", "Budget")
    object Mates : Screen("mates", "Mates")
    object BuatSplitBill : Screen("buat_split_bill", "Split Bill")
    object Profil : Screen("profil", "Profil")
}

@Composable
fun FinMatesBottomNav(currentRoute: String, onNavigate: (String) -> Unit) {
    val items = listOf(
        Screen.Beranda,
        Screen.Riwayat,
        Screen.Budget,
        Screen.Mates,
        Screen.Profil
    )

    NavigationBar(
        containerColor = Color.White
    ) {
        items.forEach { screen ->
            val selected = currentRoute == screen.route
            NavigationBarItem(
                icon = { 
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .background(
                                if (selected) FinGreenPrimary else Color.Gray,
                                shape = CircleShape
                            )
                    )
                },
                label = { Text(text = screen.title, fontSize = 11.sp, color = if (selected) FinGreenPrimary else Color.Gray) },
                selected = selected,
                onClick = {
                    if (currentRoute != screen.route) {
                        onNavigate(screen.route)
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = FinGreenPrimary.copy(alpha = 0.15f)
                )
            )
        }
    }
}
