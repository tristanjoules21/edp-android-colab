package com.example.myapplication

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme

class Activity1 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val isDark = isSystemInDarkTheme()
                val bgGradient = if (isDark) {
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF800000), // Deep Maroon
                            Color(0xFF4A0000),
                            Color(0xFF121212)  // Matte Black
                        )
                    )
                } else {
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFFFF5F5), // Light Maroon
                            Color(0xFFFDECEC),
                            Color.White        // Pure White
                        )
                    )
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(brush = bgGradient)
                            .padding(innerPadding)
                    ) {
                        BusinessCard()
                    }
                }
            }
        }
    }
}

object BusinessCardColors {
    @Composable
    fun cardBackground(): Color = if (isSystemInDarkTheme()) Color(0xFF1E1E1E) else Color.White

    @Composable
    fun textPrimary(): Color = if (isSystemInDarkTheme()) Color.White else Color(0xFF212121)

    @Composable
    fun textSecondary(): Color = if (isSystemInDarkTheme()) Color(0xFFD0D0D0) else Color(0xFF616161)

    @Composable
    fun accentSecondary(): Color = if (isSystemInDarkTheme()) Color(0xFFD4AF37) else Color(0xFF800000)

    @Composable
    fun divider(): Color = if (isSystemInDarkTheme()) Color.White.copy(alpha = 0.1f) else Color.Black.copy(alpha = 0.1f)
    
    @Composable
    fun rowBackground(): Color = if (isSystemInDarkTheme()) Color(0xFF2A2A2A) else Color(0xFFF5F5F5)
}

@Composable
fun BusinessCard(modifier: Modifier = Modifier) {
    val isDark = isSystemInDarkTheme()
    
    Box(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = 420.dp)
                .wrapContentHeight()
                .then(
                    if (!isDark) Modifier.border(0.5.dp, Color(0xFF800000).copy(alpha = 0.1f), RoundedCornerShape(24.dp))
                    else Modifier
                ),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.elevatedCardColors(containerColor = BusinessCardColors.cardBackground()),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 12.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 40.dp, horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Profile Placeholder Section
                Box(
                    modifier = Modifier
                        .size(130.dp)
                        .shadow(
                            elevation = 20.dp,
                            shape = CircleShape,
                            spotColor = if (isDark) Color(0xFFD4AF37) else Color(0xFF800000)
                        )
                        .border(2.dp, Color.White, CircleShape)
                        .clip(CircleShape)
                        .background(if (isDark) Color(0xFF121212) else Color(0xFFF8F8F8)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Profile",
                        modifier = Modifier.fillMaxSize(0.6f),
                        tint = BusinessCardColors.accentSecondary().copy(alpha = 0.8f)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Typography Section
                Text(
                    text = "Tristan Joules Pahayahay",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = BusinessCardColors.textPrimary(),
                    textAlign = TextAlign.Center,
                    letterSpacing = 0.5.sp
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = "BS Information Technology Student",
                    fontSize = 16.sp,
                    color = BusinessCardColors.accentSecondary(),
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )
                
                Text(
                    text = "UI/UX Designer • Android Developer",
                    fontSize = 14.sp,
                    color = BusinessCardColors.textSecondary(),
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(32.dp))

                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(0.9f),
                    thickness = 1.dp,
                    color = BusinessCardColors.divider()
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Contact Section
                ContactRow(
                    icon = Icons.Default.Phone,
                    text = "+63 912 345 6789"
                )

                ContactRow(
                    icon = Icons.Default.Email,
                    text = "tristanjoules@email.com"
                )

                ContactRow(
                    icon = Icons.Default.Language,
                    text = "www.tristanportfolio.com"
                )

                ContactRow(
                    icon = Icons.Default.LocationOn,
                    text = "Cagayan de Oro City, Philippines"
                )
            }
        }
    }
}

@Composable
fun ContactRow(
    icon: ImageVector,
    text: String
) {
    Surface(
        onClick = { /* Interaction */ },
        color = BusinessCardColors.rowBackground(),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = BusinessCardColors.accentSecondary(),
                modifier = Modifier.size(20.dp)
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Text(
                text = text,
                fontSize = 14.sp,
                color = BusinessCardColors.textPrimary(),
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Preview(showBackground = true, name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun BusinessCardDarkPreview() {
    MyApplicationTheme {
        val bgGradient = Brush.verticalGradient(
            colors = listOf(Color(0xFF800000), Color(0xFF4A0000), Color(0xFF121212))
        )
        Box(modifier = Modifier.fillMaxSize().background(bgGradient)) {
            BusinessCard()
        }
    }
}

@Preview(showBackground = true, name = "Light Mode", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun BusinessCardLightPreview() {
    MyApplicationTheme {
        val bgGradient = Brush.verticalGradient(
            colors = listOf(Color(0xFFFFF5F5), Color(0xFFFDECEC), Color.White)
        )
        Box(modifier = Modifier.fillMaxSize().background(bgGradient)) {
            BusinessCard()
        }
    }
}
