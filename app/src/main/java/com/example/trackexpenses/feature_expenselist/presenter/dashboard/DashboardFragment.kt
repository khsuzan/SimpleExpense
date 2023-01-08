package com.example.trackexpenses.feature_expenselist.presenter

import android.os.Bundle
import android.view.View
import com.example.trackexpenses.databinding.FragmentDashboardBinding
import com.example.trackexpenses.utils.presenter.ExpenseBaseFragment

class DashboardFragment : ExpenseBaseFragment<FragmentDashboardBinding>(FragmentDashboardBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}