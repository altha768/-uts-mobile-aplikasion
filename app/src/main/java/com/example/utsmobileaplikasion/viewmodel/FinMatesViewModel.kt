package com.example.utsmobileaplikasion.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.utsmobileaplikasion.model.DummyData
import com.example.utsmobileaplikasion.model.MateItem
import com.example.utsmobileaplikasion.model.TransactionItem
import com.example.utsmobileaplikasion.model.TransactionType

class FinMatesViewModel : ViewModel() {
    var transactions = mutableStateOf(DummyData.initialTransactions.toMutableList())
        private set

    var budgets = mutableStateOf(DummyData.initialBudgets.toMutableList())
        private set

    var mates = mutableStateOf(DummyData.initialMates.toMutableList())
        private set

    // User settings & state
    var currentUserEmail by mutableStateOf("altha@email.com")
        private set

    var currentUserName by mutableStateOf("Altha")
        private set

    var isBiometricEnabled by mutableStateOf(true)
        private set

    var isNotificationEnabled by mutableStateOf(true)
        private set

    var budgetAlertThreshold by mutableStateOf(90)
        private set

    var lastNotificationMessage by mutableStateOf<String?>(null)
        private set

    fun addTransaction(title: String, amount: Double, category: String, date: String, type: TransactionType) {
        val newId = (transactions.value.size + 1).toString()
        val newItem = TransactionItem(newId, title, amount, category, date, type)
        transactions.value = (listOf(newItem) + transactions.value).toMutableList()

        // Update budget spent if expense
        if (type == TransactionType.EXPENSE) {
            budgets.value = budgets.value.map { budget ->
                if (budget.categoryName.equals(category, ignoreCase = true)) {
                    val updatedSpent = budget.spent + amount
                    val percentage = (updatedSpent / budget.limit) * 100
                    var warning = budget.warningMessage
                    if (percentage >= budgetAlertThreshold) {
                        warning = "Peringatan: ${budget.categoryName} hampir habis (${percentage.toInt()}%)"
                        lastNotificationMessage = "Push Notification: Budget ${budget.categoryName} mencapai ${percentage.toInt()}%!"
                    }
                    budget.copy(spent = updatedSpent, warningMessage = warning)
                } else {
                    budget
                }
            }.toMutableList()
        }
    }

    fun deleteTransaction(id: String) {
        transactions.value = transactions.value.filter { it.id != id }.toMutableList()
    }

    fun addSplitBill(title: String, totalAmount: Double, splitCount: Int) {
        val perPerson = totalAmount / splitCount
        val newMate = MateItem(
            id = (mates.value.size + 1).toString(),
            name = "Split Bill: $title",
            amount = perPerson * (splitCount - 1),
            isOwedByMe = false,
            activityTitle = title,
            date = "Hari ini"
        )
        mates.value = (listOf(newMate) + mates.value).toMutableList()
        lastNotificationMessage = "Push Notification: Tagihan baru '$title' dikirim ke $splitCount teman!"
    }

    fun updateSettings(biometric: Boolean, notification: Boolean, threshold: Int) {
        isBiometricEnabled = biometric
        isNotificationEnabled = notification
        budgetAlertThreshold = threshold
    }

    fun clearNotification() {
        lastNotificationMessage = null
    }
}
