package com.example.utsmobileaplikasion.ui.screens

import androidx.compose.foundation.background
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
import com.example.utsmobileaplikasion.ui.theme.FinGreenPrimary
import com.example.utsmobileaplikasion.viewmodel.FinMatesViewModel
import java.util.Locale

@Composable
fun BudgetScreen(
    viewModel: FinMatesViewModel,
    onNavigate: (String) -> Unit,
    currentRoute: String
) {
    val budgets = viewModel.budgets.value
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
                Text(
                    text = "Budget Bulanan",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(budgets) { budget ->
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = budget.categoryName,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF222222)
                                )
                                if (budget.isLunas) {
                                    Text(
                                        text = "Lunas",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = FinGreenPrimary
                                    )
                                } else {
                                    val spentStr = String.format(localeID, "%,.0f", budget.spent).replace(',', '.')
                                    val limitStr = String.format(localeID, "%,.0f", budget.limit).replace(',', '.')
                                    Text(
                                        text = "Rp $spentStr / Rp $limitStr",
                                        fontSize = 13.sp,
                                        color = Color(0xFF666666)
                                    )
                                }
                            }

                            if (!budget.isLunas) {
                                val progress = (budget.spent / budget.limit).toFloat().coerceIn(0f, 1f)
                                LinearProgressIndicator(
                                    progress = { progress },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(8.dp),
                                    color = if (progress > 0.9f) Color(0xFFD9534F) else FinGreenPrimary,
                                    trackColor = Color(0xFFE0E0E0),
                                )
                            }

                            if (budget.warningMessage != null) {
                                Card(
                                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFCF8E3)),
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(6.dp)
                                ) {
                                    Text(
                                        text = budget.warningMessage ?: "",
                                        color = Color(0xFF8A6D3B),
                                        fontSize = 12.sp,
                                        modifier = Modifier.padding(10.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(4.dp))
                    Button(
                        onClick = { /* Add category action */ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = FinGreenPrimary),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(text = "+ Kategori Baru", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Native feature mockup banner
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFEBF3EF)),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            text = "Native: Push Notifications (Aktif - Peringatan 90%)",
                            color = FinGreenPrimary,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(14.dp)
                        )
                    }
                }
            }
        }
    }
}
