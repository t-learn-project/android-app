package ru.tinkoff.tlearn.presentation

import android.transition.Fade
import android.transition.TransitionManager
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import ru.tinkoff.tlearn.databinding.LayoutCardBinding
import ru.tinkoff.tlearn.databinding.LayoutTranslationBlockBinding
import ru.tinkoff.tlearn.databinding.LayoutWordBlockBinding
import ru.tinkoff.tlearn.domain.models.Card

class WordBlockViewHolder(
    private val binding: LayoutWordBlockBinding
) {

    fun bindTo(card: Card) = with(binding) {
        tvWord.text = card.word
        tvWordTranscription.text = card.transcription
        tvWordType.text = root.resources.getString(card.wordType.stringResId)
    }

    fun hide() = with(binding) {
        root.isVisible = false
    }

    fun show() = with(binding) {
        root.isVisible = true
    }
}

class TranslationBlockViewHolder(
    private val binding: LayoutTranslationBlockBinding
) {

    fun bindTo(card: Card) = with(binding) {
        tvWordTranslation.text = card.translation.joinToString(", ")
    }

    fun hide() = with(binding) {
        root.isVisible = false
    }

    fun show() = with(binding) {
        root.isVisible = true
    }
}

class CardViewHolder(
    private val binding: LayoutCardBinding
): RecyclerView.ViewHolder(binding.root) {

    companion object {
        const val EXPAND_ANIMATION_DURATION = 250L
    }

    var onExpandCardClickListener: (() -> Unit)? = null

    private val wordHolder0 = WordBlockViewHolder(binding.wordBlock0)
    private val wordHolder1 = WordBlockViewHolder(binding.wordBlock1)
    private val translationHolder0 = TranslationBlockViewHolder(binding.translationBlock0)
    private val translationHolder1 = TranslationBlockViewHolder(binding.translationBlock1)

    private var _item: Card? = null
    private var isDirectCard: Boolean = true

    val item: Card?
        get() = _item

    init {
        binding.btnExpandCard.setOnClickListener {
            onExpandCardClickListener?.invoke()
        }
    }

    // Items might be null if they are not paged in yet
    fun bindTo(card: Card?) {
        prepareCard()
        _item = card

        if (card != null) {
            bindWordStateTo(card)

            if (card.reversed) {
                bindAsReversedTo(card)
            } else {
                bindAsDirectTo(card)
            }
        }
    }

    private fun bindWordStateTo(card: Card) = with(binding) {
        icWordState.isVisible = true
        tvWordState.isVisible = true
        icWordState.setImageResource(card.state.iconResId)
        tvWordState.text = root.resources.getString(card.state.stringResId)
    }

    private fun bindAsReversedTo(card: Card) {
        translationHolder0.bindTo(card)
        translationHolder0.show()
        wordHolder1.bindTo(card)
        isDirectCard = false
    }

    private fun bindAsDirectTo(card: Card) {
        wordHolder0.bindTo(card)
        wordHolder0.show()
        translationHolder1.bindTo(card)
        isDirectCard = true
    }


    private fun prepareCard() = with(binding) {
        icWordState.isVisible = false
        tvWordState.isVisible = false
        divider.isVisible = false
        btnExpandCard.isVisible = true
        wordHolder0.hide()
        wordHolder1.hide()
        translationHolder0.hide()
        translationHolder1.hide()
    }

    fun expandCard() = with(binding) {
        val transition = Fade().apply {
            duration = EXPAND_ANIMATION_DURATION
            addTarget(divider)
            addTarget(wordBlock0.root)
            addTarget(wordBlock1.root)
            addTarget(translationBlock0.root)
            addTarget(translationBlock1.root)
        }

        TransitionManager.beginDelayedTransition(cardLayout, transition)

        divider.isVisible = true
        btnExpandCard.isVisible = false

        if (isDirectCard) {
            translationHolder1.show()
        } else {
            wordHolder1.show()
        }
    }
}