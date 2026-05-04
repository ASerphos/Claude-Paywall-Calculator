package com.example.calculatorpaywall.ui

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculatorpaywall.logic.CalculatorLogic
import com.example.calculatorpaywall.ui.theme.CalcBackground
import com.example.calculatorpaywall.ui.theme.CalcFunctionBtn
import com.example.calculatorpaywall.ui.theme.CalcNumberBtn
import com.example.calculatorpaywall.ui.theme.CalcOperatorBtn
import com.example.calculatorpaywall.ui.theme.TrialBannerAmber

private enum class BtnKind { Number, Function, Operator }

private data class CalcButton(
    val label: String,
    val kind: BtnKind,
    val widthWeight: Float = 1f
)

private val keypad: List<List<CalcButton>> = listOf(
    listOf(
        CalcButton("AC", BtnKind.Function),
        CalcButton("+/-", BtnKind.Function),
        CalcButton("%", BtnKind.Function),
        CalcButton("÷", BtnKind.Operator)
    ),
    listOf(
        CalcButton("7", BtnKind.Number),
        CalcButton("8", BtnKind.Number),
        CalcButton("9", BtnKind.Number),
        CalcButton("×", BtnKind.Operator)
    ),
    listOf(
        CalcButton("4", BtnKind.Number),
        CalcButton("5", BtnKind.Number),
        CalcButton("6", BtnKind.Number),
        CalcButton("−", BtnKind.Operator)
    ),
    listOf(
        CalcButton("1", BtnKind.Number),
        CalcButton("2", BtnKind.Number),
        CalcButton("3", BtnKind.Number),
        CalcButton("+", BtnKind.Operator)
    ),
    listOf(
        CalcButton("0", BtnKind.Number, widthWeight = 2f),
        CalcButton(".", BtnKind.Number),
        CalcButton("=", BtnKind.Operator)
    )
)

@Composable
fun CalculatorScreen(
    isSubscribed: Boolean,
    initialUsesLeft: Int,
    onTrialExhausted: () -> Unit,
    onUpgradeTapped: () -> Unit
) {
    val calc = remember { CalculatorLogic() }
    var display by remember { mutableStateOf(calc.display) }
    var usesLeft by remember { mutableIntStateOf(initialUsesLeft) }

    fun handlePress(btn: CalcButton) {
        when (btn.kind) {
            BtnKind.Number -> {
                if (btn.label == ".") calc.inputDecimal() else calc.inputDigit(btn.label)
            }
            BtnKind.Function -> when (btn.label) {
                "AC" -> calc.clear()
                "+/-" -> calc.toggleSign()
                "%" -> calc.percentage()
            }
            BtnKind.Operator -> {
                if (btn.label == "=") {
                    val performed = calc.calculate()
                    if (performed && !isSubscribed) {
                        usesLeft = (usesLeft - 1).coerceAtLeast(0)
                    }
                } else {
                    calc.inputOperator(btn.label)
                }
            }
        }
        display = calc.display

        if (!isSubscribed && usesLeft <= 0) {
            onTrialExhausted()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CalcBackground)
            .padding(horizontal = 16.dp)
    ) {
        Spacer(Modifier.height(48.dp))

        TopBar(isSubscribed = isSubscribed)

        if (!isSubscribed) {
            Spacer(Modifier.height(8.dp))
            TrialBanner(usesLeft = usesLeft, onClick = onUpgradeTapped)
        }

        Spacer(Modifier.weight(1f))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 16.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            Text(
                text = display,
                color = Color.White,
                fontSize = 80.sp,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.End,
                maxLines = 1
            )
        }

        Spacer(Modifier.height(8.dp))

        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            keypad.forEach { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    row.forEach { btn ->
                        CalcKey(
                            button = btn,
                            modifier = Modifier.weight(btn.widthWeight),
                            onClick = { handlePress(btn) }
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(24.dp))
    }
}

@Composable
private fun TopBar(isSubscribed: Boolean) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Calculator Pro",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(Modifier.width(8.dp))
        if (isSubscribed) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFF2A2A2A))
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.WorkspacePremium,
                        contentDescription = null,
                        tint = Color(0xFFFFB020),
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = "PRO",
                        color = Color(0xFFFFB020),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
        Spacer(Modifier.weight(1f))
    }
}

@Composable
private fun TrialBanner(usesLeft: Int, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(TrialBannerAmber.copy(alpha = 0.18f))
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.LockOpen,
            contentDescription = null,
            tint = TrialBannerAmber,
            modifier = Modifier.size(18.dp)
        )
        Spacer(Modifier.width(8.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "Free trial",
                color = TrialBannerAmber,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "$usesLeft calculation${if (usesLeft == 1) "" else "s"} remaining · Tap to upgrade",
                color = Color.White.copy(alpha = 0.85f),
                fontSize = 12.sp
            )
        }
    }
}

@Composable
private fun CalcKey(
    button: CalcButton,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val (bg, fg) = when (button.kind) {
        BtnKind.Number -> CalcNumberBtn to Color.White
        BtnKind.Function -> CalcFunctionBtn to Color.Black
        BtnKind.Operator -> CalcOperatorBtn to Color.White
    }

    val shape = if (button.widthWeight > 1f) RoundedCornerShape(40.dp) else CircleShape

    Box(
        modifier = modifier
            .height(72.dp)
            .clip(shape)
            .background(bg)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp),
        contentAlignment = if (button.widthWeight > 1f) Alignment.CenterStart else Alignment.Center
    ) {
        Text(
            text = button.label,
            color = fg,
            fontSize = if (button.label.length > 1) 26.sp else 30.sp,
            fontWeight = FontWeight.Medium
        )
    }
}
