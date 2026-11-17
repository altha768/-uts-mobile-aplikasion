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
fun MatesScreen(
    viewModel: FinMatesViewModel,
    onNavigateToSplitBill: () -> Unit,
    onNavigate: (String) -> Unit,
    currentRoute: String,
) {
    val mates = viewModel.mates.value
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
            // Header with avatars in top right
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
                        text = "Mates",
                        color = Color(0xFF111111),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                    )

                    // Avatar bubbles (B, D, R)
                    Row(
                        horizontalArrangement = Arrangement.spacedBy((-6).dp),
                    ) {
                        listOf(
                            Triple("B", Color(0xFF1565C0), Color.White),
                            Triple("D", Color(0xFF8D6E63), Color.White),
                            Triple("R", Color(0xFF512DA8), Color.White),
                        ).forEach { (initial, bg, fg) ->
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .background(bg, CircleShape),
                                contentAlignment = Alignment.Center,
                            ) {
                                Text(initial, color = fg, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
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
                    // Summary cards with action buttons (Bayar & Ingatkan)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.weight(1f),
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                            ) {
                                Text(text = "Kamu ditagih", fontSize = 12.sp, color = Color(0xFF777777))
                                Text(
                                    text = "Rp 15.000",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFD9534F),
                                )
                                Button(
                                    onClick = { /* Bayar action */ },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD9534F)),
                                    shape = RoundedCornerShape(8.dp),
                                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                                    modifier = Modifier.height(32.dp),
                                ) {
                                    Text(text = "Bayar", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
                                }
                            }
                        }

                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.weight(1f),
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                            ) {
                                Text(text = "Kamu menagih", fontSize = 12.sp, color = Color(0xFF777777))
                                Text(
                                    text = "Rp 120.000",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = FinGreenPrimary,
                                )
                                Button(
                                    onClick = { /* Ingatkan action */ },
                                    colors = ButtonDefaults.buttonColors(containerColor = FinGreenPrimary),
                                    shape = RoundedCornerShape(8.dp),
                                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                                    modifier = Modifier.height(32.dp),
                                ) {
                                    Text(text = "Ingatkan", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
                                }
                            }
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Aktivitas",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF222222),
                    )
                }

                items(mates) { mate ->
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier.fillMaxWidth(),
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
                                            text = when {
                                                mate.name.contains("Galon", true) -> "💧"
                                                mate.name.contains("Nasi", true) -> "🍛"
                                                else -> "⚡"
                                            },
                                            fontSize = 18.sp,
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(
                                        text = mate.name,
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = Color(0xFF222222),
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = mate.date,
                                        fontSize = 12.sp,
                                        color = Color(0xFF777777),
                                    )
                                }
                            }

                            val prefix = if (mate.isOwedByMe) "-" else "+"
                            val color = if (mate.isOwedByMe) Color(0xFFD9534F) else FinGreenPrimary
                            val formattedAmount = "$prefix${String.format(localeID, "%,.0f", mate.amount).replace(',', '.')}"
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
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = onNavigateToSplitBill,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = FinGreenPrimary),
                        shape = RoundedCornerShape(12.dp),
                    ) {
                        Text(text = "Bagi Tagihan", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

