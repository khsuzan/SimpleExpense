package com.example.trackexpenses.feature_expenselist.presenter.components

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.trackexpenses.databinding.FragmentAddExpenseDialogBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddExpenseDialog : DialogFragment() {

    private var _binding: FragmentAddExpenseDialogBinding? = null
    private val binding: FragmentAddExpenseDialogBinding
        get() = _binding!!

    private val viewmodel: AddExpenseViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddExpenseDialogBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.addInputTitle.addTextChangedListener {
            viewmodel.expenseTitle = it.toString()
        }
        binding.addInputAmount.addTextChangedListener {
            viewmodel.expenseAmount = it.toString()
        }

        binding.expenseSubmitBtn.setOnClickListener{
            viewmodel.submitExpense()
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewmodel.addExpenseEvent.collect{event->
                when(event){
                    AddExpenseViewModel.AddExpenseEvent.SubmittedExpense -> {
                        dismiss()
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
        println("AlertD: Dialog is dismissing")
    }
}