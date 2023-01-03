package com.example.trackexpenses.feature_expenselist.presenter.components

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
class AddExpenseViewModel @Inject constructor(
    private val expenseDAO: ExpenseDAO
) : ViewModel() {

    var expenseTitle: String = ""
    var expenseAmount: String = ""


    private val _addExpenseChannel = Channel<AddExpenseEvent>()
    val addExpenseEvent = _addExpenseChannel.receiveAsFlow();

    fun submitExpense() = viewModelScope.launch {
        if(expenseTitle.isEmpty() or expenseAmount.isEmpty()){
            // Validation
        }
        addExpense(ExpenseEntity(itemTitle = expenseTitle, amount = expenseAmount.toInt()))

    }

    private fun addExpense(expenseEntity: ExpenseEntity) = viewModelScope.launch {
        expenseDAO.insertExpense(expenseEntity)
        _addExpenseChannel.send(AddExpenseEvent.SubmittedExpense)
    }



    sealed class AddExpenseEvent {
        object SubmittedExpense : AddExpenseEvent()
    }
}