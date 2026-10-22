package com.example.utsmobileaplikasion.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.utsmobileaplikasion.ui.theme.FinGreenPrimary
import com.example.utsmobileaplikasion.viewmodel.FinMatesViewModel

@Composable
fun ProfilScreen(
    viewModel: FinMatesViewModel,
    onLogout: () -> Unit,
    onNavigate: (String) -> Unit,
    currentRoute: String
) {
    var biometric by remember { mutableStateOf(viewModel.isBiometricEnabled) }
    var notification by remember { mutableStateOf(viewModel.isNotificationEnabled) }

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
                    text = "Pengaturan",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Settings Card
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Biometric Toggle
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "Kunci biometrik", fontSize = 15.sp, color = Color(0xFF222222))
                            Switch(
                                checked = biometric,
                                onCheckedChange = {
                                    biometric = it
                                    viewModel.updateSettings(it, notification, viewModel.budgetAlertThreshold)
                                },
                                colors = SwitchDefaults.colors(checkedThumbColor = FinGreenPrimary)
                            )
                        }

                        HorizontalDivider()

                        // Notification Toggle
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "Notifikasi tagihan", fontSize = 15.sp, color = Color(0xFF222222))
                            Switch(
                                checked = notification,
                                onCheckedChange = {
                                    notification = it
                                    viewModel.updateSettings(biometric, it, viewModel.budgetAlertThreshold)
                                },
                                colors = SwitchDefaults.colors(checkedThumbColor = FinGreenPrimary)
                            )
                        }

                        HorizontalDivider()

                        // Budget Alert Threshold
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "Peringatan budget", fontSize = 15.sp, color = Color(0xFF222222))
                            Text(text = "${viewModel.budgetAlertThreshold}%", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = FinGreenPrimary)
                        }

                        HorizontalDivider()

                        // Akun Email
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "Akun", fontSize = 15.sp, color = Color(0xFF222222))
                            Text(text = viewModel.currentUserEmail, fontSize = 14.sp, fontWeight = FontWeight.Medium, color = FinGreenPrimary)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Logout Button
                Button(
                    onClick = onLogout,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD9534F)),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(text = "Keluar", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }

                // Native Feature mockup info
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFEBF3EF)),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = "Native: Biometrik + Push Notification Integrasi Aktif",
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
