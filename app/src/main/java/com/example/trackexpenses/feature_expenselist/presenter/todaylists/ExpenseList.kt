package com.example.trackexpenses.feature_expenselist.presenter

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trackexpenses.databinding.FragmentExpenseListBinding
import com.example.trackexpenses.utils.presenter.ExpenseBaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ExpenseList :
    ExpenseBaseFragment<FragmentExpenseListBinding>(FragmentExpenseListBinding::inflate) {

    private val viewModel: ExpenseViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val linearLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        val myAdapter = ExpenseListAdapter{expense->
            val action = ExpenseListDirections.actionGlobalAddExpenseDialog(expense);
            findNavController().navigate(action)
        }

        val divider = DividerItemDecoration(requireContext(), linearLayoutManager.orientation)

        binding.expenseListRecyclerView.apply {
            layoutManager = linearLayoutManager
            adapter = myAdapter
            addItemDecoration(divider)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.expenseFlow.collectLatest {
                myAdapter.submitList(it)
            }
        }

        binding.floatAddBtn.setOnClickListener {
            viewModel.addExpenseClick()
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.expenseEvent.collect { event ->
                when (event) {
                    is ExpenseViewModel.ExpenseListEvent.AddEditExpense -> {
                        val action = ExpenseListDirections.actionGlobalAddExpenseDialog(null);
                        findNavController().navigate(action)
                    }
                }
            }
        }

    }
}