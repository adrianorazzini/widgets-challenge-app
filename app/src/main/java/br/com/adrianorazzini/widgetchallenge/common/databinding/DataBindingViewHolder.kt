package br.com.adrianorazzini.widgetchallenge.common.databinding

import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView

class DataBindingViewHolder<T>(private val binding: ViewDataBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: T?, listener: BindingAdapterItemClickListener? = null) {
        if (item != null) {
            binding.setVariable(BR.item, item)
        }

        itemView.setOnClickListener {
            listener?.onClick(it, item)
        }

        binding.executePendingBindings()
    }
}