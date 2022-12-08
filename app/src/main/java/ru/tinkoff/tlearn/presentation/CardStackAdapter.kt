package ru.tinkoff.tlearn.presentation


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import ru.tinkoff.tlearn.databinding.LayoutCardBinding
import ru.tinkoff.tlearn.domain.models.Card
import javax.inject.Inject

class CardStackAdapter @Inject constructor():
    PagingDataAdapter<Card, CardViewHolder>(diffCallback) {

    var onExpandCardClickListener: ((CardViewHolder) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LayoutCardBinding.inflate(inflater, parent, false)

        return CardViewHolder(binding).also { holder ->
            holder.onExpandCardClickListener = {
                onExpandCardClickListener?.invoke(holder)
            }
        }
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Card>() {
            override fun areItemsTheSame(oldItem: Card, newItem: Card): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Card, newItem: Card): Boolean {
                return oldItem == newItem
            }
        }
    }

}