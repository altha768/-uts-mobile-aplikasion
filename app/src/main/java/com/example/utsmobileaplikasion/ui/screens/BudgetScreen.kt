package com.example.utsmobileaplikasion.ui.screens

import androidx.compose.foundation.background
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
import com.example.utsmobileaplikasion.ui.theme.FinGreenPrimary
import com.example.utsmobileaplikasion.viewmodel.FinMatesViewModel
import java.util.Locale

@Composable
fun BudgetScreen(
    viewModel: FinMatesViewModel,
    onNavigate: (String) -> Unit,
    currentRoute: String,
) {
    val budgets = viewModel.budgets.value
    val localeID = Locale.forLanguageTag("id-ID")

    val totalSpent = budgets.sumOf { it.spent }
    val totalLimit = budgets.sumOf { it.limit }
    val overallPercentage = if (totalLimit > 0) ((totalSpent / totalLimit) * 100).toInt() else 0

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
            // Header with month and + Kategori button
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
                    Column {
                        Text(
                            text = "Budget",
                            color = Color(0xFF111111),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "November 2026",
                            fontSize = 12.sp,
                            color = Color(0xFF777777),
                        )
                    }

                    Button(
                        onClick = { /* Add category */ },
                        colors = ButtonDefaults.buttonColors(containerColor = FinGreenPrimary),
                        shape = RoundedCornerShape(10.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                        modifier = Modifier.height(34.dp),
                    ) {
                        Text(text = "+ Kategori", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
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
                    // Summary Progress Card (70%, Terpakai Rp 1.055.000 dari Rp 1.500.000)
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Terpakai Rp ${String.format(localeID, "%,.0f", totalSpent).replace(',', '.')}",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF111111),
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "dari Rp ${String.format(localeID, "%,.0f", totalLimit).replace(',', '.')}",
                                    fontSize = 13.sp,
                                    color = Color(0xFF777777),
                                )
                            }

                            // Circular Progress Indicator mockup
                            Box(
                                modifier = Modifier
                                    .size(60.dp)
                                    .background(FinGreenPrimary.copy(alpha = 0.15f), CircleShape),
                                contentAlignment = Alignment.Center,
                            ) {
                                Text(
                                    text = "$overallPercentage%",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = FinGreenPrimary,
                                )
                            }
                        }
                    }
                }

                items(budgets) { budget ->
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Surface(
                                        shape = CircleShape,
                                        color = Color(0xFFF0F4F1),
                                        modifier = Modifier.size(36.dp),
                                    ) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Text(
                                                text = when (budget.categoryName.lowercase()) {
                                                    "makan" -> "🍜"
                                                    "transport" -> "🚗"
                                                    "hiburan" -> "🎬"
                                                    "kos" -> "🏠"
                                                    else -> "💡"
                                                },
                                                fontSize = 16.sp,
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Column {
                                        Text(
                                            text = budget.categoryName,
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF222222),
                                        )
                                        Spacer(modifier = Modifier.height(2.dp))
                                        val spentStr = String.format(localeID, "%,.0f", budget.spent).replace(',', '.')
                                        val limitStr = String.format(localeID, "%,.0f", budget.limit).replace(',', '.')
                                        Text(
                                            text = "Rp $spentStr / Rp $limitStr",
                                            fontSize = 12.sp,
                                            color = Color(0xFF777777),
                                        )
                                    }
                                }

                                if (budget.isLunas) {
                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        color = Color(0xFFE8F5E9),
                                    ) {
                                        Text(
                                            text = "Lunas",
                                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = FinGreenPrimary,
                                        )
                                    }
                                } else {
                                    val percent = if (budget.limit > 0) ((budget.spent / budget.limit) * 100).toInt() else 0
                                    Text(
                                        text = "$percent%",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (percent >= 90) Color(0xFFD9534F) else Color(0xFF555555),
                                    )
                                }
                            }

                            if (!budget.isLunas) {
                                val progress = (budget.spent / budget.limit).toFloat().coerceIn(0f, 1f)
                                LinearProgressIndicator(
                                    progress = { progress },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(6.dp),
                                    color = if (progress >= 0.9f) Color(0xFFD9534F) else FinGreenPrimary,
                                    trackColor = Color(0xFFF0F0F0),
                                )
                            }

                            if (budget.warningMessage != null) {
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = Color(0xFFFFF8E1),
                                    modifier = Modifier.fillMaxWidth(),
                                ) {
                                    Text(
                                        text = "⚠️ ${budget.warningMessage}",
                                        color = Color(0xFF8F6B00),
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Medium,
                                        modifier = Modifier.padding(10.dp),
                                    )
                                }
                            }
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

