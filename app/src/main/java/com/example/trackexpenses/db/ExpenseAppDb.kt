package com.example.trackexpenses.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.trackexpenses.feature_expenselist.data.ExpenseDAO
import com.example.trackexpenses.feature_expenselist.domain.ExpenseEntity

@Database(entities = [ExpenseEntity::class], version = 1)
abstract class ExpenseAppDb : RoomDatabase() {
    abstract fun expenseListDao(): ExpenseDAO

    companion object {
        const val DB_NAME = "expense_db"
        const val EXPENSES_LISTS_TABLE = "expenses_lists"
    }
}