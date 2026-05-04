package com.example.calculatorpaywall

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.calculatorpaywall.ui.CalculatorScreen
import com.example.calculatorpaywall.ui.PaywallScreen
import com.example.calculatorpaywall.ui.theme.CalculatorPaywallTheme

private const val FREE_TRIAL_LIMIT = 5

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculatorPaywallTheme {
                AppRoot()
            }
        }
    }
}

private sealed interface Screen {
    data class Paywall(val trialEnded: Boolean) : Screen
    data class Calculator(val isSubscribed: Boolean, val initialUsesLeft: Int) : Screen
}

@Composable
private fun AppRoot() {
    var screen by remember { mutableStateOf<Screen>(Screen.Paywall(trialEnded = false)) }

    when (val current = screen) {
        is Screen.Paywall -> PaywallScreen(
            trialEnded = current.trialEnded,
            onStartTrial = {
                screen = Screen.Calculator(
                    isSubscribed = false,
                    initialUsesLeft = FREE_TRIAL_LIMIT
                )
            },
            onSubscribe = {
                screen = Screen.Calculator(
                    isSubscribed = true,
                    initialUsesLeft = 0
                )
            },
            onRestore = {
                screen = Screen.Calculator(
                    isSubscribed = true,
                    initialUsesLeft = 0
                )
            }
        )
        is Screen.Calculator -> CalculatorScreen(
            isSubscribed = current.isSubscribed,
            initialUsesLeft = current.initialUsesLeft,
            onTrialExhausted = {
                screen = Screen.Paywall(trialEnded = true)
            },
            onUpgradeTapped = {
                screen = Screen.Paywall(trialEnded = false)
            }
        )
    }
}
