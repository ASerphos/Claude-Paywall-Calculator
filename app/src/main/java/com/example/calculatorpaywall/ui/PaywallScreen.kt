package com.example.calculatorpaywall.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculatorpaywall.ui.theme.BrandPink
import com.example.calculatorpaywall.ui.theme.BrandPurple
import com.example.calculatorpaywall.ui.theme.DeepBlue
import com.example.calculatorpaywall.ui.theme.MidnightBlue

private data class Plan(
    val id: String,
    val title: String,
    val price: String,
    val period: String,
    val tagline: String?,
    val isBestValue: Boolean = false
)

private val plans = listOf(
    Plan("monthly", "Monthly", "$4.99", "/month", tagline = "Cancel anytime"),
    Plan("yearly", "Yearly", "$29.99", "/year", tagline = "Save 50%", isBestValue = true)
)

@Composable
fun PaywallScreen(
    trialEnded: Boolean,
    onStartTrial: () -> Unit,
    onSubscribe: () -> Unit,
    onRestore: () -> Unit
) {
    var selectedPlan by remember { mutableStateOf("yearly") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(BrandPurple, DeepBlue, MidnightBlue)
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(24.dp))

            Box(
                modifier = Modifier
                    .size(88.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.linearGradient(listOf(BrandPink, BrandPurple))
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Calculate,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(48.dp)
                )
            }

            Spacer(Modifier.height(16.dp))
            Text(
                text = "Calculator Pro",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = if (trialEnded) "Your free trial has ended" else "Unlock unlimited calculations",
                color = Color.White.copy(alpha = 0.75f),
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(28.dp))

            FeatureList()

            Spacer(Modifier.height(28.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                plans.forEach { plan ->
                    PlanCard(
                        plan = plan,
                        selected = plan.id == selectedPlan,
                        modifier = Modifier.weight(1f),
                        onClick = { selectedPlan = plan.id }
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = onSubscribe,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = BrandPurple
                )
            ) {
                Text(
                    text = if (trialEnded) "Subscribe Now" else "Start Free Trial",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(Modifier.height(12.dp))

            if (!trialEnded) {
                TextButton(onClick = onStartTrial) {
                    Text(
                        text = "Try for free (5 calculations)",
                        color = Color.White,
                        fontSize = 15.sp,
                        textDecoration = TextDecoration.Underline
                    )
                }
            } else {
                TextButton(onClick = onRestore) {
                    Text(
                        text = "Restore Purchases",
                        color = Color.White,
                        fontSize = 15.sp,
                        textDecoration = TextDecoration.Underline
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            Text(
                text = "Terms of Service · Privacy Policy",
                color = Color.White.copy(alpha = 0.5f),
                fontSize = 12.sp
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Payment will be charged to your account at confirmation.\nSubscription auto-renews unless cancelled 24 hours\nbefore the end of the current period.",
                color = Color.White.copy(alpha = 0.4f),
                fontSize = 11.sp,
                textAlign = TextAlign.Center,
                lineHeight = 14.sp
            )
            Spacer(Modifier.height(16.dp))
        }
    }
}

@Composable
private fun FeatureList() {
    val features = listOf(
        "Unlimited calculations",
        "Calculation history",
        "Scientific functions",
        "No ads, ever"
    )
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        features.forEach { feature ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.18f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
                Spacer(Modifier.width(12.dp))
                Text(
                    text = feature,
                    color = Color.White,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
private fun PlanCard(
    plan: Plan,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val borderColor = if (selected) Color.White else Color.White.copy(alpha = 0.25f)
    val borderWidth = if (selected) 2.dp else 1.dp

    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White.copy(alpha = if (selected) 0.18f else 0.08f))
                .border(borderWidth, borderColor, RoundedCornerShape(16.dp))
                .clickable(onClick = onClick)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = plan.title,
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = plan.price,
                color = Color.White,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = plan.period,
                color = Color.White.copy(alpha = 0.7f),
                fontSize = 12.sp
            )
            if (plan.tagline != null) {
                Spacer(Modifier.height(8.dp))
                Text(
                    text = plan.tagline,
                    color = Color.White.copy(alpha = 0.85f),
                    fontSize = 11.sp
                )
            }
        }
        if (plan.isBestValue) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 0.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(BrandPink)
                    .padding(horizontal = 10.dp, vertical = 3.dp)
            ) {
                Text(
                    text = "BEST VALUE",
                    color = Color.White,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
