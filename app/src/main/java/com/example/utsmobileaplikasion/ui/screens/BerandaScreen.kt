package com.example.utsmobileaplikasion.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.utsmobileaplikasion.model.TransactionType
import com.example.utsmobileaplikasion.ui.theme.FinGreenPrimary
import com.example.utsmobileaplikasion.viewmodel.FinMatesViewModel
import java.util.Locale

@Composable
fun BerandaScreen(
    viewModel: FinMatesViewModel,
    onNavigateToAdd: () -> Unit,
    onNavigateToDetail: (String) -> Unit,
    onNavigate: (String) -> Unit,
    currentRoute: String
) {
    val transactions = viewModel.transactions.value
    val totalIncome = transactions.filter { it.type == TransactionType.INCOME }.sumOf { it.amount }
    val totalExpense = transactions.filter { it.type == TransactionType.EXPENSE }.sumOf { it.amount }
    val balance = 1250000.0 + totalIncome - totalExpense

    val localeID = Locale("id", "ID")

    Scaffold(
        bottomBar = {
            FinMatesBottomNav(currentRoute = currentRoute, onNavigate = onNavigate)
        },
        containerColor = Color(0xFFFAFAFA)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(FinGreenPrimary)
                    .padding(20.dp)
            ) {
                Column {
                    Text(
                        text = "Halo, ${viewModel.currentUserName}",
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.height(12.dp))
                    // Sisa uang card
                    Card(
                        colors = CardDefaults.cardColors(containerColor = FinGreenPrimary),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = "Sisa uang bulan ini", color = Color.White.copy(alpha = 0.8f), fontSize = 13.sp)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Rp ${String.format(localeID, "%,.0f", balance).replace(',', '.')}",
                                color = Color.White,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                item {
                    // Aman per hari card
                    Card(
                        colors = CardDefaults.cardColors(containerColor = FinGreenPrimary.copy(alpha = 0.85f)),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = "Aman per hari (20 hari lagi)", color = Color.White.copy(alpha = 0.8f), fontSize = 13.sp)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Rp ${String.format(localeID, "%,.0f", balance / 20).replace(',', '.')}",
                                color = Color.White,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                item {
                    // + Tambah Transaksi Button
                    Button(
                        onClick = onNavigateToAdd,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = FinGreenPrimary),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(text = "+ Tambah Transaksi", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Terbaru",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF222222)
                    )
                }

                items(transactions.take(4)) { tx ->
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onNavigateToDetail(tx.id) }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(text = tx.title, fontSize = 15.sp, fontWeight = FontWeight.Medium, color = Color(0xFF222222))
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(text = "${tx.category} • ${tx.date}", fontSize = 12.sp, color = Color(0xFF777777))
                            }
                            val prefix = if (tx.type == TransactionType.INCOME) "+" else "-"
                            val color = if (tx.type == TransactionType.INCOME) Color(0xFF5CB85C) else Color(0xFFD9534F)
                            val formattedAmount = "$prefix${String.format(localeID, "%,.0f", tx.amount).replace(',', '.')}"
                            Text(
                                text = formattedAmount,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = color
                            )
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}
