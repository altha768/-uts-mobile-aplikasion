package com.example.utsmobileaplikasion.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
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
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 8.dp
    ) {
        // 1. Beranda
        NavigationBarItem(
            icon = { Text("🏠", fontSize = 16.sp) },
            label = { Text(text = "Beranda", fontSize = 11.sp, color = if (currentRoute == Screen.Beranda.route) FinGreenPrimary else Color.Gray) },
            selected = currentRoute == Screen.Beranda.route,
            onClick = { if (currentRoute != Screen.Beranda.route) onNavigate(Screen.Beranda.route) },
            colors = NavigationBarItemDefaults.colors(indicatorColor = FinGreenPrimary.copy(alpha = 0.15f))
        )

        // 2. Riwayat
        NavigationBarItem(
            icon = { Text("⏱️", fontSize = 16.sp) },
            label = { Text(text = "Riwayat", fontSize = 11.sp, color = if (currentRoute == Screen.Riwayat.route) FinGreenPrimary else Color.Gray) },
            selected = currentRoute == Screen.Riwayat.route,
            onClick = { if (currentRoute != Screen.Riwayat.route) onNavigate(Screen.Riwayat.route) },
            colors = NavigationBarItemDefaults.colors(indicatorColor = FinGreenPrimary.copy(alpha = 0.15f))
        )

        // 3. Center '+' Button
        NavigationBarItem(
            icon = {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .background(FinGreenPrimary, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "+", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                }
            },
            label = { },
            selected = currentRoute == Screen.TambahTransaksi.route,
            onClick = { if (currentRoute != Screen.TambahTransaksi.route) onNavigate(Screen.TambahTransaksi.route) },
            colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent)
        )

        // 4. Budget
        NavigationBarItem(
            icon = { Text("📊", fontSize = 16.sp) },
            label = { Text(text = "Budget", fontSize = 11.sp, color = if (currentRoute == Screen.Budget.route) FinGreenPrimary else Color.Gray) },
            selected = currentRoute == Screen.Budget.route,
            onClick = { if (currentRoute != Screen.Budget.route) onNavigate(Screen.Budget.route) },
            colors = NavigationBarItemDefaults.colors(indicatorColor = FinGreenPrimary.copy(alpha = 0.15f))
        )

        // 5. Mates
        NavigationBarItem(
            icon = { Text("👥", fontSize = 16.sp) },
            label = { Text(text = "Mates", fontSize = 11.sp, color = if (currentRoute == Screen.Mates.route) FinGreenPrimary else Color.Gray) },
            selected = currentRoute == Screen.Mates.route,
            onClick = { if (currentRoute != Screen.Mates.route) onNavigate(Screen.Mates.route) },
            colors = NavigationBarItemDefaults.colors(indicatorColor = FinGreenPrimary.copy(alpha = 0.15f))
        )
    }
}

