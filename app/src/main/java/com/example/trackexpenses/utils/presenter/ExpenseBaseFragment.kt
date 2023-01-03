package com.example.trackexpenses.utils.presenter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class ExpenseBaseFragment<VB : ViewBinding>(private val fragmentInflater: (LayoutInflater) -> VB) :
    Fragment() {

    private var _binding: VB? = null
    val binding: VB
        get() = _binding as VB;

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = fragmentInflater(inflater);
        if(_binding==null)
            throw IllegalArgumentException("Binding cannot be null")
        return binding.root;

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null;
    }
}