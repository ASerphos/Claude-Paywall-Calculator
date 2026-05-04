package com.example.calculatorpaywall.logic

class CalculatorLogic {
    var display: String = "0"
        private set

    private var operand1: Double = 0.0
    private var operator: String = ""
    private var shouldResetDisplay: Boolean = false
    private var justCalculated: Boolean = false

    fun inputDigit(digit: String) {
        if (justCalculated) {
            display = digit
            justCalculated = false
            shouldResetDisplay = false
            return
        }
        if (shouldResetDisplay) {
            display = digit
            shouldResetDisplay = false
        } else {
            display = if (display == "0") digit else display + digit
        }
    }

    fun inputDecimal() {
        if (justCalculated) {
            display = "0."
            justCalculated = false
            shouldResetDisplay = false
            return
        }
        if (shouldResetDisplay) {
            display = "0."
            shouldResetDisplay = false
            return
        }
        if (!display.contains(".")) {
            display = "$display."
        }
    }

    fun inputOperator(op: String) {
        justCalculated = false
        if (operator.isNotEmpty() && !shouldResetDisplay) {
            performCalculation()
        }
        operand1 = display.toDoubleOrNull() ?: 0.0
        operator = op
        shouldResetDisplay = true
    }

    /**
     * Returns true if a calculation actually occurred (used to count trial usage).
     */
    fun calculate(): Boolean {
        if (operator.isEmpty()) return false
        performCalculation()
        operator = ""
        shouldResetDisplay = true
        justCalculated = true
        return true
    }

    fun clear() {
        display = "0"
        operand1 = 0.0
        operator = ""
        shouldResetDisplay = false
        justCalculated = false
    }

    fun toggleSign() {
        val value = display.toDoubleOrNull() ?: 0.0
        display = formatResult(value * -1)
    }

    fun percentage() {
        val value = display.toDoubleOrNull() ?: 0.0
        display = formatResult(value / 100.0)
    }

    private fun performCalculation() {
        val operand2 = display.toDoubleOrNull() ?: 0.0
        val result = when (operator) {
            "+" -> operand1 + operand2
            "−" -> operand1 - operand2
            "×" -> operand1 * operand2
            "÷" -> if (operand2 != 0.0) operand1 / operand2 else 0.0
            else -> return
        }
        display = formatResult(result)
    }

    private fun formatResult(value: Double): String {
        if (value.isNaN() || value.isInfinite()) return "Error"
        if (value == value.toLong().toDouble() && kotlin.math.abs(value) < 1e15) {
            return value.toLong().toString()
        }
        return value.toString().trimEnd('0').trimEnd('.')
    }
}
