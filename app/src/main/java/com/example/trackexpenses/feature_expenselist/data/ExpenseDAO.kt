package com.example.trackexpenses.feature_expenselist.data

import androidx.room.*
import com.example.trackexpenses.db.ExpenseAppDb.Companion.EXPENSES_LISTS_TABLE
import com.example.trackexpenses.feature_expenselist.domain.ExpenseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDAO {

    @Insert
    suspend fun insertExpense(expense: ExpenseEntity)

    @Delete
    suspend fun deleteExpense(expense: ExpenseEntity)

    @Update
    suspend fun updateExpense(expense: ExpenseEntity)

    @Query("SELECT * FROM $EXPENSES_LISTS_TABLE")
    fun getExpenseList(): Flow<List<ExpenseEntity>>
}