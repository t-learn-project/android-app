package ru.tinkoff.tlearn.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import ru.tinkoff.tlearn.databinding.LayoutCardPlaceholderBinding
import javax.inject.Inject

class CardLoadStateAdapter @Inject constructor():
    LoadStateAdapter<CardLoadStateViewHolder>() {

    var onRetryClickListener: (() -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): CardLoadStateViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LayoutCardPlaceholderBinding.inflate(inflater, parent, false)

        return CardLoadStateViewHolder(binding).also { holder ->
            holder.onRetryClickListener = {
                onRetryClickListener?.invoke()
            }
        }
    }

    override fun onBindViewHolder(
        holder: CardLoadStateViewHolder,
        loadState: LoadState
    ) = holder.bind(loadState)

}