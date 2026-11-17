package com.example.utsmobileaplikasion.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
    currentRoute: String,
) {
    val transactions = viewModel.transactions.value
    val totalIncome = transactions.filter { it.type == TransactionType.INCOME }.sumOf { it.amount }
    val totalExpense = transactions.filter { it.type == TransactionType.EXPENSE }.sumOf { it.amount }
    val balance = 1250000.0 + totalIncome - totalExpense

    val localeID = Locale.forLanguageTag("id-ID")

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
            // Header with greeting and date
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
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .background(FinGreenPrimary, CircleShape),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = viewModel.currentUserName.take(1).uppercase(),
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "Selasa, 3 Nov",
                                fontSize = 11.sp,
                                color = Color(0xFF777777),
                            )
                            Text(
                                text = "Halo, ${viewModel.currentUserName}",
                                color = Color(0xFF111111),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                    }

                    Surface(
                        shape = CircleShape,
                        color = Color(0xFFF0F0F0),
                        modifier = Modifier.size(36.dp),
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text("🔔", fontSize = 14.sp)
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
                    // Sisa uang card (White card with deep green accent or light green bg matching design)
                    Card(
                        colors = CardDefaults.cardColors(containerColor = FinGreenPrimary),
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Column(modifier = Modifier.padding(20.dp)) {
                            Text(
                                text = "Sisa uang bulan ini",
                                color = Color.White.copy(alpha = 0.85f),
                                fontSize = 13.sp,
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Rp ${String.format(localeID, "%,.0f", balance).replace(',', '.')}",
                                color = Color.White,
                                fontSize = 26.sp,
                                fontWeight = FontWeight.Bold,
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = Color.White.copy(alpha = 0.2f),
                            ) {
                                Text(
                                    text = "Aman Rp 159.150/hari · 20 hari lagi",
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color.White,
                                )
                            }
                        }
                    }
                }

                item {
                    // Quick Action Buttons Row (Catat, Split Bill, Budget, Mates)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        QuickActionButton(
                            icon = "➕",
                            label = "Catat",
                            onClick = onNavigateToAdd,
                        )
                        QuickActionButton(
                            icon = "⚡",
                            label = "Split Bill",
                            onClick = { onNavigate(Screen.BuatSplitBill.route) },
                        )
                        QuickActionButton(
                            icon = "📊",
                            label = "Budget",
                            onClick = { onNavigate(Screen.Budget.route) },
                        )
                        QuickActionButton(
                            icon = "👥",
                            label = "Mates",
                            onClick = { onNavigate(Screen.Mates.route) },
                        )
                    }
                }

                item {
                    // Kamu ditagih & Kamu menagih summary row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier.weight(1f),
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Text(text = "Kamu ditagih", fontSize = 12.sp, color = Color(0xFF777777))
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Rp 15.000",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFD9534F),
                                )
                            }
                        }

                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier.weight(1f),
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Text(text = "Kamu menagih", fontSize = 12.sp, color = Color(0xFF777777))
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Rp 120.000",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF5CB85C),
                                )
                            }
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Terbaru",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF222222),
                    )
                }

                items(transactions.take(4)) { tx ->
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onNavigateToDetail(tx.id) },
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
                                    color = Color(0xFFEFEFEF),
                                    modifier = Modifier.size(40.dp),
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Text(
                                            text = when (tx.category.lowercase()) {
                                                "makan" -> "🍜"
                                                "transport" -> "🚗"
                                                "hiburan" -> "🎬"
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
                                        text = "${tx.category} • ${tx.date}",
                                        fontSize = 12.sp,
                                        color = Color(0xFF777777),
                                    )
                                }
                            }
                            val prefix = if (tx.type == TransactionType.INCOME) "+" else "-"
                            val color = if (tx.type == TransactionType.INCOME) Color(0xFF5CB85C) else Color(0xFFD9534F)
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

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
fun QuickActionButton(icon: String, label: String, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(onClick = onClick),
    ) {
        Surface(
            shape = CircleShape,
            color = Color.White,
            modifier = Modifier.size(54.dp),
            shadowElevation = 2.dp,
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(text = icon, fontSize = 20.sp)
            }
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF333333),
        )
    }
}

