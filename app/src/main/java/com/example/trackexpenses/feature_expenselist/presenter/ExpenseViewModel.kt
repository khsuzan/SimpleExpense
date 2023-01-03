package com.example.trackexpenses.feature_expenselist.presenter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trackexpenses.feature_expenselist.data.ExpenseDAO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpenseViewModel @Inject constructor(
    private val expenseDAO: ExpenseDAO
) : ViewModel() {

    private val _expenseEventChannel = Channel<ExpenseListEvent>()
    val expenseEvent = _expenseEventChannel.receiveAsFlow();

    val expenseFlow = expenseDAO.getExpenseList()

    fun addExpenseClick() = viewModelScope.launch{
        _expenseEventChannel.send(ExpenseListEvent.AddExpense)
    }

    sealed class ExpenseListEvent {
        object AddExpense : ExpenseListEvent()
    }

}