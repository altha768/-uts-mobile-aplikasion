package com.example.utsmobileaplikasion.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.utsmobileaplikasion.ui.theme.FinGreenPrimary
import com.example.utsmobileaplikasion.viewmodel.FinMatesViewModel
import java.util.Locale

@Composable
fun BuatSplitBillScreen(
    viewModel: FinMatesViewModel,
    onSplitSuccess: () -> Unit,
    onBack: () -> Unit,
) {
    var title by remember { mutableStateOf("Listrik kos") }
    var totalAmountStr by remember { mutableStateOf("80000") }
    var splitCount by remember { mutableIntStateOf(4) }
    var isBagiRata by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val localeID = Locale.forLanguageTag("id-ID")
    val total = totalAmountStr.toDoubleOrNull() ?: 0.0
    val perPerson = if (splitCount > 0) total / splitCount else 0.0

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFFAFAFA),
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Header with back button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBack) {
                        Text(text = "‹", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Buat Split Bill",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF222222)
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (errorMessage != null) {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFDE8E8)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = errorMessage ?: "",
                            color = Color(0xFFD9534F),
                            fontSize = 13.sp,
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                }

                // Judul Tagihan Card
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "Judul tagihan", fontSize = 12.sp, color = Color(0xFF777777))
                        Spacer(modifier = Modifier.height(4.dp))
                        OutlinedTextField(
                            value = title,
                            onValueChange = { title = it },
                            placeholder = { Text("Contoh: Listrik kos") },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = FinGreenPrimary,
                                unfocusedBorderColor = Color(0xFFE0E0E0)
                            ),
                            shape = RoundedCornerShape(10.dp)
                        )
                    }
                }

                // Total Nominal Card
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "Total nominal", fontSize = 12.sp, color = Color(0xFF777777))
                        Spacer(modifier = Modifier.height(4.dp))
                        OutlinedTextField(
                            value = totalAmountStr,
                            onValueChange = { totalAmountStr = it },
                            prefix = { Text("Rp ", fontWeight = FontWeight.Bold) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = FinGreenPrimary,
                                unfocusedBorderColor = Color(0xFFE0E0E0)
                            ),
                            shape = RoundedCornerShape(10.dp)
                        )
                    }
                }

                // Toggle Bagi Rata vs Custom
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFEEEEEE), RoundedCornerShape(12.dp))
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Button(
                        onClick = { isBagiRata = true },
                        modifier = Modifier.weight(1f).height(40.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isBagiRata) FinGreenPrimary else Color.Transparent
                        ),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            text = "Bagi rata",
                            color = if (isBagiRata) Color.White else Color(0xFF555555),
                            fontWeight = FontWeight.Medium,
                            fontSize = 13.sp
                        )
                    }

                    Button(
                        onClick = { isBagiRata = false },
                        modifier = Modifier.weight(1f).height(40.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (!isBagiRata) FinGreenPrimary else Color.Transparent
                        ),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            text = "Custom",
                            color = if (!isBagiRata) Color.White else Color(0xFF555555),
                            fontWeight = FontWeight.Medium,
                            fontSize = 13.sp
                        )
                    }
                }

                // Info Badge
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = Color(0xFFE8F5E9)
                ) {
                    val formattedPerPerson = String.format(localeID, "%,.0f", perPerson).replace(',', '.')
                    Text(
                        text = "$splitCount orang · Rp $formattedPerPerson/orang",
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2E7D32)
                    )
                }

                // Participants List Card
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        ListOfParticipants(perPerson, localeID)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Kirim Tagihan Button
                Button(
                    onClick = {
                        if (title.isBlank() || total <= 0) {
                            errorMessage = "Judul dan total nominal harus valid!"
                        } else {
                            viewModel.addSplitBill(title, total, splitCount)
                            onSplitSuccess()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = FinGreenPrimary),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(text = "Kirim Tagihan", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun ListOfParticipants(perPerson: Double, localeID: Locale) {
    val participants = listOf(
        Triple("Kamu", Color(0xFF2E7D32), Color.White),
        Triple("Budi", Color(0xFF1565C0), Color.White),
        Triple("Dina", Color(0xFF8D6E63), Color.White),
        Triple("Raka", Color(0xFF512DA8), Color.White)
    )

    participants.forEach { (name, bg, fg) ->
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(bg, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = name.take(1),
                        color = fg,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(text = name, fontSize = 15.sp, fontWeight = FontWeight.Medium, color = Color(0xFF222222))
            }

            val amountStr = String.format(localeID, "%,.0f", perPerson).replace(',', '.')
            Text(
                text = "Rp $amountStr",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF222222)
            )
        }
    }
}

