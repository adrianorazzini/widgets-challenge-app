package br.com.adrianorazzini.widgetchallenge.ui.home.adapter

import androidx.recyclerview.widget.DiffUtil
import br.com.adrianorazzini.widgetchallenge.R
import br.com.adrianorazzini.widgetchallenge.common.databinding.DataBindingAdapter
import br.com.adrianorazzini.widgetchallenge.common.databinding.DataBindingViewHolder
import br.com.adrianorazzini.widgetchallenge.ui.home.listener.CardButtonClickListener
import br.com.adrianorazzini.widgetchallenge.ui.home.model.CardViewItem
import kotlinx.android.synthetic.main.cardview_home.view.*

class CardListAdapter(private val buttonClickListener: CardButtonClickListener) :
    DataBindingAdapter<CardViewItem>(DiffCallback()) {

    override fun getItemViewType(position: Int): Int {
        return R.layout.cardview_home
    }

    override fun onBindViewHolder(holder: DataBindingViewHolder<CardViewItem>, position: Int) {
        val item = getItem(position)
        holder.itemView.cardButton.tag = item.identifier
        holder.itemView.cardButton.setOnClickListener {
            buttonClickListener.onButtonClick(it, position)
        }

        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<CardViewItem>() {

        override fun areItemsTheSame(oldItem: CardViewItem, newItem: CardViewItem)
                : Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: CardViewItem, newItem: CardViewItem)
                : Boolean {
            return oldItem == newItem
        }
    }
}