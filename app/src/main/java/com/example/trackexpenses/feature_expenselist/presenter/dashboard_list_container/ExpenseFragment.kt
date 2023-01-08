package com.example.trackexpenses.feature_expenselist.presenter.todaylists


import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.trackexpenses.databinding.FragmentExpenseBinding
import com.example.trackexpenses.utils.presenter.ExpenseBaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExpenseFragment :
    ExpenseBaseFragment<FragmentExpenseBinding>(FragmentExpenseBinding::inflate) {

    private val viewModel: ExpenseViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        binding.floatAddBtn.setOnClickListener {
            viewModel.addExpenseClick()
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.expenseEvent.collect { event ->
                when (event) {
                    is ExpenseViewModel.ExpenseEvent.NavigateAddDialog -> {
                        // Add Expense.
                        // @param[event.expense] will null here
                        // By passing null mean dialog show for new entry
                        val action = ExpenseFragmentDirections
                            .actionGlobalAddExpenseDialog(event.expense)
                        findNavController().navigate(action)
                    }
                }
            }
        }

    }
}