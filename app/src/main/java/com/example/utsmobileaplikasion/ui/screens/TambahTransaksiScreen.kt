package com.example.utsmobileaplikasion.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
    onBack: () -> Unit
) {
    var isExpense by remember { mutableStateOf(true) }
    var nominal by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Makan") }
    var date by remember { mutableStateOf("29 Sep 2026") }
    var note by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFFAFAFA)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
                    .background(FinGreenPrimary)
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                Text(
                    text = "Tambah Transaksi",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
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
                            fontSize = 14.sp,
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                }

                // Type Toggle
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = { isExpense = true },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isExpense) FinGreenPrimary else Color.LightGray
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(text = "Pengeluaran", color = Color.White)
                    }

                    Button(
                        onClick = { isExpense = false },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (!isExpense) FinGreenPrimary else Color.LightGray
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(text = "Pemasukan", color = Color.White)
                    }
                }

                // Nominal field (Number keyboard)
                OutlinedTextField(
                    value = nominal,
                    onValueChange = { nominal = it; errorMessage = null },
                    label = { Text("Nominal") },
                    placeholder = { Text("25000") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp)
                )

                // Kategori field
                OutlinedTextField(
                    value = category,
                    onValueChange = { category = it },
                    label = { Text("Kategori") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp)
                )

                // Tanggal field
                OutlinedTextField(
                    value = date,
                    onValueChange = { date = it },
                    label = { Text("Tanggal") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp)
                )

                // Catatan field
                OutlinedTextField(
                    value = note,
                    onValueChange = { note = it },
                    label = { Text("Catatan") },
                    placeholder = { Text("Makan siang kantin") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Simpan Button
                Button(
                    onClick = {
                        val amountVal = nominal.toDoubleOrNull()
                        if (nominal.isBlank() || amountVal == null || amountVal <= 0) {
                            errorMessage = "Nominal harus berupa angka valid lebih dari 0!"
                        } else if (category.isBlank()) {
                            errorMessage = "Kategori wajib diisi!"
                        } else {
                            val titleText = if (note.isNotBlank()) note else category
                            viewModel.addTransaction(
                                title = titleText,
                                amount = amountVal,
                                category = category,
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
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(text = "Simpan", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
