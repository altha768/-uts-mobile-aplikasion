package com.example.utsmobileaplikasion.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.utsmobileaplikasion.model.TransactionItem
import com.example.utsmobileaplikasion.model.TransactionType
import com.example.utsmobileaplikasion.ui.theme.FinGreenPrimary
import com.example.utsmobileaplikasion.viewmodel.FinMatesViewModel
import java.util.Locale

@Composable
fun RiwayatTransaksiScreen(
    viewModel: FinMatesViewModel,
    onNavigateToDetail: (String) -> Unit,
    onNavigate: (String) -> Unit,
    currentRoute: String,
) {
    val transactions = viewModel.transactions.value
    val localeID = Locale.forLanguageTag("id-ID")

    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf("Semua") } // Semua, Pengeluaran, Pemasukan

    val filteredList = transactions.filter { tx ->
        val matchesQuery = tx.title.contains(searchQuery, ignoreCase = true) ||
            tx.category.contains(searchQuery, ignoreCase = true)
        val matchesFilter = when (selectedFilter) {
            "Pengeluaran" -> tx.type == TransactionType.EXPENSE
            "Pemasukan" -> tx.type == TransactionType.INCOME
            else -> true
        }
        matchesQuery && matchesFilter
    }

    val totalIncome = transactions.filter { it.type == TransactionType.INCOME }.sumOf { it.amount }
    val totalExpense = transactions.filter { it.type == TransactionType.EXPENSE }.sumOf { it.amount }

    Scaffold(
        bottomBar = {
            FinMatesBottomNav(
                currentRoute = currentRoute,
                onNavigate = onNavigate,
            )
        },
        containerColor = Color(0xFFFAFAFA),
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            // Header with Riwayat title and clock icon
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(horizontal = 20.dp, vertical = 16.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "Riwayat",
                        color = Color(0xFF111111),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                    )

                    Surface(
                        shape = CircleShape,
                        color = Color(0xFFF0F0F0),
                        modifier = Modifier.size(36.dp),
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text("🕒", fontSize = 14.sp)
                        }
                    }
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                item {
                    Spacer(modifier = Modifier.height(4.dp))
                    // Search Bar
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = { Text("Cari transaksi atau catatan", fontSize = 14.sp, color = Color(0xFF888888)) },
                        leadingIcon = { Text("🔍", fontSize = 16.sp) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            disabledContainerColor = Color.White,
                            focusedBorderColor = FinGreenPrimary,
                            unfocusedBorderColor = Color(0xFFE0E0E0),
                        ),
                    )
                }

                item {
                    // Filter Chips (Semua, Pengeluaran, Pemasukan)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        listOf("Semua", "Pengeluaran", "Pemasukan").forEach { filter ->
                            val isSelected = selectedFilter == filter
                            Surface(
                                modifier = Modifier
                                    .clickable { selectedFilter = filter },
                                shape = RoundedCornerShape(20.dp),
                                color = if (isSelected) FinGreenPrimary else Color.White,
                                shadowElevation = if (isSelected) 2.dp else 0.dp,
                            ) {
                                Text(
                                    text = filter,
                                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = if (isSelected) Color.White else Color(0xFF555555),
                                )
                            }
                        }
                    }
                }

                item {
                    // Summary Cards (Masuk & Keluar)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.weight(1f),
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(text = "Masuk", fontSize = 12.sp, color = Color(0xFF777777))
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Rp ${String.format(localeID, "%,.0f", totalIncome).replace(',', '.')}",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = FinGreenPrimary,
                                )
                            }
                        }

                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.weight(1f),
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(text = "Keluar", fontSize = 12.sp, color = Color(0xFF777777))
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Rp ${String.format(localeID, "%,.0f", totalExpense).replace(',', '.')}",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFD9534F),
                                )
                            }
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(text = "Hari ini", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF222222))
                        val todayTotal = filteredList.filter { it.date.contains("Hari", true) }.let { list ->
                            val inc = list.filter { it.type == TransactionType.INCOME }.sumOf { it.amount }
                            val exp = list.filter { it.type == TransactionType.EXPENSE }.sumOf { it.amount }
                            inc - exp
                        }
                        val sign = if (todayTotal >= 0) "+" else ""
                        Text(
                            text = "Total $sign${String.format(localeID, "%,.0f", todayTotal).replace(',', '.')}",
                            fontSize = 12.sp,
                            color = Color(0xFF777777),
                        )
                    }
                }

                val todayList = filteredList.filter { it.date.contains("Hari", true) }
                items(todayList) { tx ->
                    TransactionRowCard(tx = tx, localeID = localeID, onClick = { onNavigateToDetail(tx.id) })
                }

                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(text = "Kemarin", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF222222))
                        val yesterdayTotal = filteredList.filter { !it.date.contains("Hari", true) }.let { list ->
                            val inc = list.filter { it.type == TransactionType.INCOME }.sumOf { it.amount }
                            val exp = list.filter { it.type == TransactionType.EXPENSE }.sumOf { it.amount }
                            inc - exp
                        }
                        val sign = if (yesterdayTotal >= 0) "+" else ""
                        Text(
                            text = "Total $sign${String.format(localeID, "%,.0f", yesterdayTotal).replace(',', '.')}",
                            fontSize = 12.sp,
                            color = Color(0xFF777777),
                        )
                    }
                }

                val yesterdayList = filteredList.filter { !it.date.contains("Hari", true) }
                items(yesterdayList) { tx ->
                    TransactionRowCard(tx = tx, localeID = localeID, onClick = { onNavigateToDetail(tx.id) })
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
fun TransactionRowCard(tx: TransactionItem, localeID: Locale, onClick: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(14.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    shape = CircleShape,
                    color = Color(0xFFF0F4F1),
                    modifier = Modifier.size(40.dp),
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = when (tx.category.lowercase()) {
                                "makan" -> "🍜"
                                "transport" -> "🚗"
                                "hiburan" -> "🎬"
                                "pendidikan" -> "📚"
                                "pemasukan" -> "➕"
                                else -> "💡"
                            },
                            fontSize = 18.sp,
                        )
                    }
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = tx.title,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF222222),
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = tx.category,
                        fontSize = 12.sp,
                        color = Color(0xFF777777),
                    )
                }
            }
            val prefix = if (tx.type == TransactionType.INCOME) "+" else "-"
            val color = if (tx.type == TransactionType.INCOME) FinGreenPrimary else Color(0xFFD9534F)
            val formattedAmount = "$prefix${String.format(localeID, "%,.0f", tx.amount).replace(',', '.')}"
            Text(
                text = formattedAmount,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = color,
            )
        }
    }
}

