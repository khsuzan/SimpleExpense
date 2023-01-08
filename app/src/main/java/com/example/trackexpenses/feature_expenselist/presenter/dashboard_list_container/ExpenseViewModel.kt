package com.example.trackexpenses.feature_expenselist.presenter.todaylists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trackexpenses.feature_expenselist.data.ExpenseDAO
import com.example.trackexpenses.feature_expenselist.domain.ExpenseEntity
import com.example.trackexpenses.utils.presenter.ExpenseTimeUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpenseViewModel @Inject constructor(
    private val expenseDAO: ExpenseDAO
) : ViewModel() {



    private val _expenseEventChannel = Channel<ExpenseEvent>()
    val expenseEvent = _expenseEventChannel.receiveAsFlow();

    val expenseFlow = expenseDAO.getTodaysExpenseList(ExpenseTimeUtil.TODAY_START,ExpenseTimeUtil.TODAY_END)

    fun addExpenseClick() = viewModelScope.launch{
        _expenseEventChannel.send(ExpenseEvent.NavigateAddDialog(null))
    }

    fun editExpenseClick(item:ExpenseEntity) = viewModelScope.launch {
        _expenseEventChannel.send(ExpenseEvent.NavigateAddDialog(item))
    }


    sealed class ExpenseEvent {
        data class NavigateAddDialog(val expense: ExpenseEntity?=null):ExpenseEvent()
    }

}