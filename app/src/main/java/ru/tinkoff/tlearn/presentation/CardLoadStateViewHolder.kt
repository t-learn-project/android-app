package ru.tinkoff.tlearn.presentation

import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import ru.tinkoff.tlearn.databinding.LayoutCardPlaceholderBinding

class CardLoadStateViewHolder(
    private val binding: LayoutCardPlaceholderBinding
): RecyclerView.ViewHolder(binding.root) {

    var onRetryClickListener: (() -> Unit)? = null

    init {
        binding.btnRetry.setOnClickListener {
            onRetryClickListener?.invoke()
        }
    }

    fun bind(loadState: LoadState) {
        prepare()

        when(loadState) {
            is LoadState.Loading -> bindAsLoading()
            is LoadState.Error   -> bindAsError()
            else -> { /* Do nothing */ }
        }
    }

    private fun prepare() = with(binding) {
        btnRetry.isVisible = false
        tvErrorTitle.isVisible = false
        ivErrorLogo.isVisible = false
        shimmerContainer.isVisible = false

        if (shimmerContainer.isShimmerStarted)
            shimmerContainer.stopShimmer()

        if (shimmerContainer.isShimmerVisible)
            shimmerContainer.hideShimmer()
    }

    private fun bindAsLoading() = with(binding) {
        shimmerContainer.isVisible = true
        shimmerContainer.showShimmer(true)
    }

    private fun bindAsError() = with(binding) {
        tvErrorTitle.isVisible = true
        ivErrorLogo.isVisible = true
        btnRetry.isVisible = true
    }

}