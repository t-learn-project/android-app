package ru.tinkoff.tlearn.presentation

import android.transition.Fade
import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import ru.tinkoff.tlearn.R
import ru.tinkoff.tlearn.domain.models.Card

class WordBlockViewHolder(
    private val root: ViewGroup
) {
    private val tvWord = root.findViewById<TextView>(R.id.tv_word)
    private val tvWordTranscription = root.findViewById<TextView>(R.id.tv_word_transcription)
    private val tvWordType = root.findViewById<TextView>(R.id.tv_word_type)

    fun bindTo(card: Card) {
        tvWord.text = card.word
        tvWordTranscription.text = card.transcription
        tvWordType.text = root.resources.getString(card.wordType.stringResId)
    }

    fun hide() {
        root.isVisible = false
    }

    fun show() {
        root.isVisible = true
    }
}

class TranslationBlockViewHolder(
    private val root: ViewGroup
) {
    private val tvWordTranslation = root.findViewById<TextView>(R.id.tv_word_translation)

    fun bindTo(card: Card) {
        tvWordTranslation.text = card.translation.joinToString(", ")
    }

    fun hide() {
        root.isVisible = false
    }

    fun show() {
        root.isVisible = true
    }
}

class CardViewHolder(
    private val root: View
): RecyclerView.ViewHolder(root) {
    private val icWordState = root.findViewById<ImageView>(R.id.ic_word_state)
    private val tvWordState = root.findViewById<TextView>(R.id.tv_word_state)

    private val placeholderBlock = root.findViewById<ViewGroup>(R.id.placeholder_block)
    private val placeholderContainer = root.findViewById<ShimmerFrameLayout>(R.id.placeholder_container)

    // Visible before card expanded
    private val wordBlock0 = root.findViewById<ViewGroup>(R.id.word_block_0)
    private val translationBlock0 = root.findViewById<ViewGroup>(R.id.translation_block_0)
    // Visible after card expanded
    private val wordBlock1 = root.findViewById<ViewGroup>(R.id.word_block_1)
    private val translationBlock1 = root.findViewById<ViewGroup>(R.id.translation_block_1)

    private val wordHolder0 = WordBlockViewHolder(wordBlock0)
    private val wordHolder1 = WordBlockViewHolder(wordBlock1)
    private val translationHolder0 = TranslationBlockViewHolder(translationBlock0)
    private val translationHolder1 = TranslationBlockViewHolder(translationBlock1)

    private val divider = root.findViewById<View>(R.id.divider)
    private val btnExpand = root.findViewById<ImageButton>(R.id.btn_expand_card)
    private val cardLayout = root.findViewById<ConstraintLayout>(R.id.card_layout)

    private var item: Card? = null

    companion object {
        const val EXPAND_ANIMATION_DURATION = 250L
    }

    private var isDirectCard: Boolean = true


    init {
        btnExpand.setOnClickListener {
            expandCard()
        }
    }

    // Items might be null if they are not paged in yet
    fun bindTo(card: Card?) {
        prepareCard()
        item = card

        if (card != null) {
            bindWordStateTo(card)
            showExpandButton()

            if (card.reversed) {
                bindAsReversedTo(card)
            } else {
                bindAsDirectTo(card)
            }

        } else {
            showLoadingPlaceholder()
        }
    }

    private fun bindWordStateTo(card: Card) {
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

    private fun showLoadingPlaceholder() {
        placeholderBlock.isVisible = true
        placeholderContainer.showShimmer(true)
    }

    private fun showExpandButton() {
        btnExpand.isEnabled = true
        btnExpand.isVisible = true
    }

    private fun prepareCard() {
        icWordState.isVisible = false
        tvWordState.isVisible = false
        divider.isVisible = false
        btnExpand.isEnabled = false
        btnExpand.isVisible = false
        placeholderContainer.hideShimmer()
        placeholderBlock.isVisible = false

        wordHolder0.hide()
        wordHolder1.hide()
        translationHolder0.hide()
        translationHolder1.hide()
    }

    private fun expandCard() {
        val transition = Fade().apply {
            duration = EXPAND_ANIMATION_DURATION
            addTarget(divider)
            addTarget(wordBlock0)
            addTarget(wordBlock1)
            addTarget(translationBlock0)
            addTarget(translationBlock1)
        }

        TransitionManager.beginDelayedTransition(cardLayout, transition)
        divider.isVisible = true
        btnExpand.isVisible = false

        if (isDirectCard) {
            translationHolder1.show()
        } else {
            wordHolder1.show()
        }
    }
}