package com.example.trackexpenses.feature_expenselist.presenter.components

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.trackexpenses.databinding.FragmentAddExpenseDialogBinding
import com.example.trackexpenses.feature_expenselist.presenter.components.AddEditExpenseViewModel.FieldType.EXPENSE_AMOUNT
import com.example.trackexpenses.feature_expenselist.presenter.components.AddEditExpenseViewModel.FieldType.EXPENSE_TITLE
import com.example.trackexpenses.utils.presenter.editableToString
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddEditExpenseDialog : DialogFragment() {

    private var _binding: FragmentAddExpenseDialogBinding? = null
    private val binding: FragmentAddExpenseDialogBinding
        get() = _binding!!

    private val viewModel: AddEditExpenseViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddExpenseDialogBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            addInputTitle.text = viewModel.expenseTitle.editableToString()
            addInputAmount.text = viewModel.expenseAmount.editableToString()
            expenseSubmitBtn.text = viewModel.btnText

        }

        binding.addInputTitle.addTextChangedListener {
            viewModel.expenseTitle = it.toString()
        }
        binding.addInputAmount.addTextChangedListener {
            viewModel.expenseAmount = it.toString()
        }

        binding.expenseSubmitBtn.setOnClickListener {
            viewModel.submitExpense()
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.addExpenseEvent.collect { event ->
                when (event) {
                    is AddEditExpenseViewModel.AddExpenseEvent.SubmittedExpense -> {
                        dismiss()
                    }
                    is AddEditExpenseViewModel.AddExpenseEvent.FieldValidationError -> {
                        when (event.field) {
                            EXPENSE_TITLE -> {
                                binding.addInputLayTitle.error = event.error
                            }
                            EXPENSE_AMOUNT -> {
                                binding.addInputLayAmount.error = event.error
                            }
                        }
                    }
                    is AddEditExpenseViewModel.AddExpenseEvent.SubmissionError -> {
                        Toast.makeText(requireContext(), event.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}