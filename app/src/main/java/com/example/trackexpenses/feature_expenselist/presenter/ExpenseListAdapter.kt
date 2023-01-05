package com.example.trackexpenses.feature_expenselist.presenter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.trackexpenses.databinding.ItemExpenselistBinding
import com.example.trackexpenses.feature_expenselist.domain.ExpenseEntity

class ExpenseListAdapter(val onItemClick:(expense:ExpenseEntity)->Unit) :
    ListAdapter<ExpenseEntity, ExpenseListAdapter.MyViewHolder>(DiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemExpenselistBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class MyViewHolder(private val itemExpenseView: ItemExpenselistBinding) :
        RecyclerView.ViewHolder(itemExpenseView.root) {
        init {
            itemView.setOnClickListener {
                val expenseItem = getItem(adapterPosition)
                onItemClick(expenseItem)
            }
        }
        fun bind(item: ExpenseEntity) {
            itemExpenseView.itemExpenseListTitle.text = item.itemTitle
            itemExpenseView.itemExpenseListAmount.text = item.amount.toString()
        }

    }

    class DiffCallback : DiffUtil.ItemCallback<ExpenseEntity>() {
        override fun areItemsTheSame(oldItem: ExpenseEntity, newItem: ExpenseEntity) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ExpenseEntity, newItem: ExpenseEntity) =
            oldItem == newItem
    }
}