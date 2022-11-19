package ru.tinkoff.tlearn.presentation


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import ru.tinkoff.tlearn.R
import ru.tinkoff.tlearn.domain.models.Card
import javax.inject.Inject

class CardStackAdapter @Inject constructor():
    PagingDataAdapter<Card, CardViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val layout = when(viewType) {
            VIEW_TYPE_DIRECT  -> R.layout.layout_card_direct
            VIEW_TYPE_REVERSE -> R.layout.layout_card_reverse
            else -> throw RuntimeException("Unknown view type: $viewType")
        }

        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(layout, parent, false)
        return CardViewHolder(view, viewType)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        val card = getItem(position)
            ?: return VIEW_TYPE_DIRECT

        return if (card.reversed) {
            VIEW_TYPE_REVERSE
        } else {
            VIEW_TYPE_DIRECT
        }
    }

    companion object {
        const val VIEW_TYPE_DIRECT  = 0
        const val VIEW_TYPE_REVERSE = 1

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