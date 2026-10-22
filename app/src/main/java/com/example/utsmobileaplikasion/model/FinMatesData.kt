package com.example.utsmobileaplikasion.model

data class TransactionItem(
    val id: String,
    val title: String,
    val amount: Double,
    val category: String,
    val date: String,
    val type: TransactionType // INCOME or EXPENSE
)

enum class TransactionType {
    INCOME, EXPENSE
}

data class BudgetItem(
    val id: String,
    val categoryName: String,
    val spent: Double,
    val limit: Double,
    val isLunas: Boolean = false,
    val warningMessage: String? = null
)

data class MateItem(
    val id: String,
    val name: String,
    val amount: Double,
    val isOwedByMe: Boolean, // true if user owes money, false if user is owed money
    val activityTitle: String,
    val date: String
)

object DummyData {
    val initialTransactions = mutableListOf(
        TransactionItem("1", "Makan siang", 25000.0, "Makan", "Hari ini", TransactionType.EXPENSE),
        TransactionItem("2", "Transport", 12000.0, "Transport", "Hari ini", TransactionType.EXPENSE),
        TransactionItem("3", "Kiriman ortu", 2000000.0, "Pemasukan", "Kemarin", TransactionType.INCOME),
        TransactionItem("4", "Ayam geprek", 22000.0, "Makan", "Kemarin", TransactionType.EXPENSE),
        TransactionItem("5", "Print tugas", 8000.0, "Pendidikan", "Kemarin", TransactionType.EXPENSE)
    )

    val initialBudgets = mutableListOf(
        BudgetItem("1", "Makan", 600000.0, 900000.0, false),
        BudgetItem("2", "Transport", 150000.0, 300000.0, false),
        BudgetItem("3", "Hiburan", 280000.0, 300000.0, false, "Peringatan: Hiburan hampir habis (93%)"),
        BudgetItem("4", "Kos", 0.0, 1500000.0, true)
    )

    val initialMates = mutableListOf(
        MateItem("1", "Budi - Galon", 15000.0, true, "Budi - Galon", "Hari ini"),
        MateItem("2", "Dina - Nasi goreng", 40000.0, false, "Dina - Nasi goreng", "Kemarin"),
        MateItem("3", "Raka - Listrik kos", 80000.0, false, "Raka - Listrik kos", "2 hari lalu")
    )
}
