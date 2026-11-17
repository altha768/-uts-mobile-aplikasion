package com.example.utsmobileaplikasion.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import com.example.utsmobileaplikasion.model.TransactionType
import com.example.utsmobileaplikasion.ui.theme.FinGreenPrimary
import com.example.utsmobileaplikasion.viewmodel.FinMatesViewModel

@Composable
fun TambahTransaksiScreen(
    viewModel: FinMatesViewModel,
    onSaveSuccess: () -> Unit,
    onBack: () -> Unit,
) {
    var isExpense by remember { mutableStateOf(true) }
    var nominal by remember { mutableStateOf("25000") }
    var selectedCategory by remember { mutableStateOf("Makan") }
    var date by remember { mutableStateOf("29 Sep 2026") }
    var note by remember { mutableStateOf("Makan siang kantin") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val categories = listOf(
        Triple("Makan", "🍜", Color(0xFFE8F5E9)),
        Triple("Transport", "🚗", Color(0xFFE3F2FD)),
        Triple("Hiburan", "🎬", Color(0xFFFFF3E0)),
        Triple("Kos", "🏠", Color(0xFFFCE4EC)),
        Triple("Belanja", "🛍️", Color(0xFFF3E5F5)),
        Triple("Lainnya", "💡", Color(0xFFECEFF1))
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFFAFAFA),
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Header with X close button
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
                        Text(text = "✕", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Tambah Transaksi",
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

                // Type Toggle (Segmented control)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFEEEEEE), RoundedCornerShape(12.dp))
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Button(
                        onClick = { isExpense = true },
                        modifier = Modifier.weight(1f).height(40.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isExpense) FinGreenPrimary else Color.Transparent
                        ),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            text = "Pengeluaran",
                            color = if (isExpense) Color.White else Color(0xFF555555),
                            fontWeight = FontWeight.Medium,
                            fontSize = 13.sp
                        )
                    }

                    Button(
                        onClick = { isExpense = false },
                        modifier = Modifier.weight(1f).height(40.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (!isExpense) FinGreenPrimary else Color.Transparent
                        ),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            text = "Pemasukan",
                            color = if (!isExpense) Color.White else Color(0xFF555555),
                            fontWeight = FontWeight.Medium,
                            fontSize = 13.sp
                        )
                    }
                }

                // Nominal Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(text = "Nominal", fontSize = 12.sp, color = Color(0xFF777777))
                        Spacer(modifier = Modifier.height(4.dp))
                        OutlinedTextField(
                            value = nominal,
                            onValueChange = { nominal = it; errorMessage = null },
                            placeholder = { Text("0") },
                            prefix = { Text("Rp ", fontWeight = FontWeight.Bold) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = FinGreenPrimary,
                                unfocusedBorderColor = Color(0xFFE0E0E0)
                            ),
                            shape = RoundedCornerShape(10.dp)
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        // Quick Chips (10rb, 25rb, 50rb, 100rb)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            listOf("10rb" to "10000", "25rb" to "25000", "50rb" to "50000", "100rb" to "100000").forEach { pair ->
                                val chipLabel = pair.first
                                val chipValue = pair.second
                                Surface(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clickable { nominal = chipValue },
                                    shape = RoundedCornerShape(8.dp),
                                    color = Color(0xFFF0F4F1)
                                ) {
                                    Box(
                                        modifier = Modifier.padding(vertical = 6.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(text = chipLabel, fontSize = 12.sp, fontWeight = FontWeight.Medium, color = FinGreenPrimary)
                                    }
                                }
                            }
                        }
                    }
                }

                // Kategori Selection
                Column {
                    Text(text = "Kategori", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF333333))
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        categories.take(3).forEach { (catName, icon, bg) ->
                            val isSelected = selectedCategory == catName
                            Surface(
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { selectedCategory = catName },
                                shape = RoundedCornerShape(12.dp),
                                color = if (isSelected) FinGreenPrimary else bg
                            ) {
                                Column(
                                    modifier = Modifier.padding(vertical = 12.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(text = icon, fontSize = 20.sp)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = catName,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = if (isSelected) Color.White else Color(0xFF333333)
                                    )
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        categories.drop(3).forEach { (catName, icon, bg) ->
                            val isSelected = selectedCategory == catName
                            Surface(
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { selectedCategory = catName },
                                shape = RoundedCornerShape(12.dp),
                                color = if (isSelected) FinGreenPrimary else bg
                            ) {
                                Column(
                                    modifier = Modifier.padding(vertical = 12.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(text = icon, fontSize = 20.sp)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = catName,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = if (isSelected) Color.White else Color(0xFF333333)
                                    )
                                }
                            }
                        }
                    }
                }

                // Tanggal & Catatan
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedTextField(
                        value = date,
                        onValueChange = { date = it },
                        label = { Text("Tanggal") },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp)
                    )

                    OutlinedTextField(
                        value = note,
                        onValueChange = { note = it },
                        label = { Text("Catatan") },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp)
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Simpan Button
                Button(
                    onClick = {
                        val amountVal = nominal.toDoubleOrNull()
                        if (nominal.isBlank() || amountVal == null || amountVal <= 0) {
                            errorMessage = "Nominal harus berupa angka valid lebih dari 0!"
                        } else {
                            val titleText = if (note.isNotBlank()) note else selectedCategory
                            viewModel.addTransaction(
                                title = titleText,
                                amount = amountVal,
                                category = selectedCategory,
                                date = date,
                                type = if (isExpense) TransactionType.EXPENSE else TransactionType.INCOME
                            )
                            onSaveSuccess()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = FinGreenPrimary),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(text = "Simpan", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

