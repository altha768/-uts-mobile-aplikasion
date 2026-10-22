package com.example.utsmobileaplikasion.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
fun DetailTransaksiScreen(
    viewModel: FinMatesViewModel,
    transactionId: String,
    onBack: () -> Unit,
    onDeleted: () -> Unit
) {
    val transaction = viewModel.transactions.value.find { it.id == transactionId }
    val localeID = Locale("id", "ID")

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
                    text = "Detail Transaksi (ID: $transactionId)",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (transaction == null) {
                    Text(text = "Transaksi tidak ditemukan.", color = Color.Gray, fontSize = 16.sp)
                    Button(onClick = onBack) {
                        Text("Kembali")
                    }
                } else {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(text = "Judul / Catatan", fontSize = 13.sp, color = Color.Gray)
                            Text(text = transaction.title, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF222222))

                            HorizontalDivider()

                            Text(text = "Nominal", fontSize = 13.sp, color = Color.Gray)
                            val prefix = if (transaction.type == TransactionType.INCOME) "+" else "-"
                            val color = if (transaction.type == TransactionType.INCOME) Color(0xFF5CB85C) else Color(0xFFD9534F)
                            val formattedAmount = "$prefix Rp ${String.format(localeID, "%,.0f", transaction.amount).replace(',', '.')}"
                            Text(text = formattedAmount, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = color)

                            HorizontalDivider()

                            Text(text = "Kategori", fontSize = 13.sp, color = Color.Gray)
                            Text(text = transaction.category, fontSize = 16.sp, fontWeight = FontWeight.Medium, color = Color(0xFF222222))

                            HorizontalDivider()

                            Text(text = "Tanggal", fontSize = 13.sp, color = Color.Gray)
                            Text(text = transaction.date, fontSize = 16.sp, fontWeight = FontWeight.Medium, color = Color(0xFF222222))
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = {
                            viewModel.deleteTransaction(transaction.id)
                            onDeleted()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD9534F)),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(text = "Hapus Transaksi", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }

                    OutlinedButton(
                        onClick = onBack,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(text = "Kembali", fontSize = 16.sp, fontWeight = FontWeight.Medium)
                    }
                }
            }
        }
    }
}
