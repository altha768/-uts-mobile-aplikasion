package com.example.utsmobileaplikasion.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SplashOnboardingScreen(
    onStartClicked: () -> Unit,
    onLoginClick: () -> Unit = onStartClicked,
) {
    val limeColor = Color(0xFFC6F454)

    // Floating / Bouncing Animations
    val infiniteTransition = rememberInfiniteTransition(label = "floating")

    val card1OffsetY by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -12f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "card1",
    )

    val card2OffsetY by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 14f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "card2",
    )

    val pillOffsetY by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -8f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1300, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "pill",
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF1B3B2B), // Deep forest green matching design
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start,
        ) {
            Column {
                Spacer(modifier = Modifier.height(16.dp))

                // Logo Header with FinMates overlapping circles icon
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .background(Color(0xFF254B38), shape = RoundedCornerShape(20.dp))
                        .padding(horizontal = 14.dp, vertical = 8.dp),
                ) {
                    // Overlapping circles icon
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .background(Color(0xFF2E5F4A), shape = CircleShape),
                        contentAlignment = Alignment.Center,
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy((-6).dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(12.dp)
                                    .background(limeColor, CircleShape),
                            )
                            Box(
                                modifier = Modifier
                                    .size(12.dp)
                                    .background(Color.White.copy(alpha = 0.9f), CircleShape),
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "FinMates",
                        color = limeColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                    )
                }

                Spacer(modifier = Modifier.height(28.dp))

                // Overlapping & Tilted Cards Container with Floating Animation
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(230.dp),
                ) {
                    // Card 1: Balance (White, rotated & animated translationY)
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .graphicsLayer(
                                translationY = card1OffsetY,
                                rotationZ = -3f,
                            ),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                    ) {
                        Column(
                            modifier = Modifier.padding(18.dp),
                        ) {
                            Text(
                                text = "Sisa uang bulan ini",
                                fontSize = 12.sp,
                                color = Color(0xFF666666),
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Rp 3.183.000",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF111111),
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = limeColor.copy(alpha = 0.4f),
                            ) {
                                Text(
                                    text = "Aman Rp 159.150/hari",
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF1B3B2B),
                                )
                            }
                        }
                    }

                    // Card 2: Split Bill (Lime green, rotated & animated translationY)
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(0.82f)
                            .align(Alignment.BottomEnd)
                            .graphicsLayer(
                                translationY = card2OffsetY,
                                rotationZ = 4f,
                            ),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = limeColor),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Column {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(text = "📄", fontSize = 12.sp)
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = "Listrik kos",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 13.sp,
                                            color = Color(0xFF1B3B2B),
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = "Rp 20.000/orang",
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF1B3B2B),
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            // Avatar circles
                            Row(
                                horizontalArrangement = Arrangement.spacedBy((-6).dp),
                            ) {
                                listOf(
                                    Triple("A", Color(0xFF1B3B2B), Color.White),
                                    Triple("B", Color(0xFF1565C0), Color.White),
                                    Triple("D", Color(0xFF8D6E63), Color.White),
                                    Triple("R", Color(0xFF512DA8), Color.White),
                                ).forEach { (initial, bg, fg) ->
                                    Box(
                                        modifier = Modifier
                                            .size(24.dp)
                                            .background(bg, CircleShape),
                                        contentAlignment = Alignment.Center,
                                    ) {
                                        Text(initial, color = fg, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Warning Pill Badge with Floating Animation
                Surface(
                    modifier = Modifier.graphicsLayer(translationY = pillOffsetY),
                    shape = RoundedCornerShape(20.dp),
                    color = Color.White,
                    shadowElevation = 4.dp,
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .background(Color(0xFFF57C00), CircleShape),
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Hiburan hampir habis",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF222222),
                        )
                    }
                }
            }

            // Bottom Section: Tagline & Buttons
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    text = buildAnnotatedString {
                        append("Kelola uang,\n")
                        withStyle(style = SpanStyle(color = limeColor)) {
                            append("bareng teman.")
                        }
                    },
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    lineHeight = 38.sp,
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Catat pengeluaran, pantau budget, dan bagi tagihan kos tanpa drama.",
                    fontSize = 13.sp,
                    color = Color(0xFFB0BEC5),
                    lineHeight = 18.sp,
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = onStartClicked,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = limeColor),
                    shape = RoundedCornerShape(14.dp),
                ) {
                    Text(
                        text = "Mulai",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1B3B2B),
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = "Sudah punya akun? ",
                        fontSize = 13.sp,
                        color = Color(0xFFB0BEC5),
                    )
                    Text(
                        text = "Masuk",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.clickable(onClick = onLoginClick),
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}



