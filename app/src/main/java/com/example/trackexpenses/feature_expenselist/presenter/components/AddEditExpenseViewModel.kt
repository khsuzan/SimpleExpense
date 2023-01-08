package com.example.trackexpenses.feature_expenselist.presenter.addedit_dialog

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trackexpenses.feature_expenselist.data.ExpenseDAO
import com.example.trackexpenses.feature_expenselist.domain.ExpenseEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditExpenseViewModel @Inject constructor(
    private val expenseDAO: ExpenseDAO,
    private val state: SavedStateHandle
) : ViewModel() {

    private val _addExpenseChannel by lazy { Channel<AddExpenseEvent>() }
    val addExpenseEvent = _addExpenseChannel.receiveAsFlow();

    val expense = state.get<ExpenseEntity>("expense")


    var expenseTitle: String = expense?.itemTitle ?: ""
        set(value) {
            field = value
            if (value.isNotBlank()) clearTitleError()
        }


    var expenseAmount: String = (expense?.amount ?: "").toString()
        set(value) {
            field = value
            if (value.isNotBlank()) clearAmountError()
        }
    val btnText: String
        get() = if (expense == null) "Add" else "Update"


    fun submitExpense() = viewModelScope.launch {
        if (expenseTitle.isBlank()) {
            showTitleError()
            return@launch
        }
        if (expenseAmount.isBlank()) {
            showAmountError()
            return@launch
        }
        if (expense != null) {
            updateExpense(ExpenseEntity(id = expense.id,itemTitle = expenseTitle, amount = expenseAmount.toDouble(), createdAt = expense.createdAt))
        } else {
            addExpense(ExpenseEntity(itemTitle = expenseTitle, amount = expenseAmount.toDouble()))
        }

    }


    private fun addExpense(expenseEntity: ExpenseEntity) = viewModelScope.launch {
        val data: Long = expenseDAO.insertExpense(expenseEntity)
        if (data > 0) {
            _addExpenseChannel.send(AddExpenseEvent.SubmittedExpense)
        } else {
            _addExpenseChannel.send(AddExpenseEvent.SubmissionError("Sorry something is wrong.Try again"))
        }
    }

    private fun updateExpense(expenseEntity: ExpenseEntity) = viewModelScope.launch {
        println(expenseEntity)
        val data: Int = expenseDAO.updateExpense(expenseEntity)
        if (data > 0) {
            _addExpenseChannel.send(AddExpenseEvent.SubmittedExpense)
        } else {
            _addExpenseChannel.send(AddExpenseEvent.SubmissionError("Sorry something is wrong.Try again"))
        }
    }

    private suspend fun showTitleError() {
        _addExpenseChannel.send(
            AddExpenseEvent.FieldValidationError(
                FieldType.EXPENSE_TITLE,
                "Title cannot be empty"
            )
        )
    }

    private suspend fun showAmountError() {
        _addExpenseChannel.send(
            AddExpenseEvent.FieldValidationError(
                FieldType.EXPENSE_AMOUNT,
                "Amount cannot be empty"
            )
        )
    }

    private fun clearTitleError() {
        viewModelScope.launch {
            _addExpenseChannel.send(
                AddExpenseEvent.FieldValidationError(
                    FieldType.EXPENSE_TITLE,
                    null
                )
            )
        }
    }

    private fun clearAmountError() {
        viewModelScope.launch {
            _addExpenseChannel.send(
                AddExpenseEvent.FieldValidationError(
                    FieldType.EXPENSE_AMOUNT,
                    null
                )
            )
        }
    }


    enum class FieldType {
        EXPENSE_TITLE,
        EXPENSE_AMOUNT
    }


    sealed class AddExpenseEvent {
        data class FieldValidationError(val field: FieldType, val error: String? = null) :
            AddExpenseEvent()

        object SubmittedExpense : AddExpenseEvent()
        data class SubmissionError(val error: String? = null) : AddExpenseEvent()
    }
}