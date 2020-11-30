package br.com.adrianorazzini.widgetchallenge.ui.statement.adapter

import androidx.recyclerview.widget.DiffUtil
import br.com.adrianorazzini.remote.model.Transaction
import br.com.adrianorazzini.widgetchallenge.R
import br.com.adrianorazzini.widgetchallenge.common.databinding.DataBindingAdapter
import br.com.adrianorazzini.widgetchallenge.common.databinding.DataBindingViewHolder

class StatementListAdapter() : DataBindingAdapter<Transaction>(DiffCallback()) {

    override fun getItemViewType(position: Int): Int {
        return R.layout.statement_item
    }

    override fun onBindViewHolder(holder: DataBindingViewHolder<Transaction>, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<Transaction>() {

        override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction)
                : Boolean {
            return oldItem.label == newItem.label
                    && oldItem.value == newItem.value
                    && oldItem.description == newItem.description
        }

        override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction)
                : Boolean {
            return oldItem == newItem
        }
    }
}