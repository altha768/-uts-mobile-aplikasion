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
fun MatesScreen(
    viewModel: FinMatesViewModel,
    onNavigateToSplitBill: () -> Unit,
    onNavigate: (String) -> Unit,
    currentRoute: String
) {
    val mates = viewModel.mates.value
    val localeID = Locale("id", "ID")

    val totalOwed = mates.filter { it.isOwedByMe }.sumOf { it.amount }
    val totalLending = mates.filter { !it.isOwedByMe }.sumOf { it.amount }

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
                    text = "Mates",
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
                item {
                    // Summary cards
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFD9534F)),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(text = "Kamu ditagih", color = Color.White.copy(alpha = 0.9f), fontSize = 12.sp)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Rp ${String.format(localeID, "%,.0f", totalOwed).replace(',', '.')}",
                                    color = Color.White,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Card(
                            colors = CardDefaults.cardColors(containerColor = FinGreenPrimary),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(text = "Kamu menagih", color = Color.White.copy(alpha = 0.9f), fontSize = 12.sp)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Rp ${String.format(localeID, "%,.0f", totalLending).replace(',', '.')}",
                                    color = Color.White,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "Aktivitas", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF222222))
                }

                items(mates) { mate ->
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(text = mate.name, fontSize = 15.sp, fontWeight = FontWeight.Medium, color = Color(0xFF222222))
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(text = mate.date, fontSize = 12.sp, color = Color(0xFF777777))
                            }
                            val prefix = if (mate.isOwedByMe) "-" else "+"
                            val color = if (mate.isOwedByMe) Color(0xFFD9534F) else Color(0xFF5CB85C)
                            val formattedAmount = "$prefix${String.format(localeID, "%,.0f", mate.amount).replace(',', '.')}"
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
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = onNavigateToSplitBill,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = FinGreenPrimary),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(text = "+ Bagi Tagihan", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFEBF3EF)),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            text = "Native: Push Notifications (Siap mengirim notifikasi ke teman)",
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
