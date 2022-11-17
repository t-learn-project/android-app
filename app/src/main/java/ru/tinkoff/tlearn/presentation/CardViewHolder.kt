package ru.tinkoff.tlearn.presentation

import android.transition.Fade
import android.transition.TransitionManager
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import ru.tinkoff.tlearn.R
import ru.tinkoff.tlearn.domain.models.Card

class CardViewHolder(
    root: View,
    private val type: Int
): RecyclerView.ViewHolder(root) {
    private val resources = root.resources

    private val icWordState = root.findViewById<ImageView>(R.id.ic_word_state)
    private val tvWordState = root.findViewById<TextView>(R.id.tv_word_state)
    private val tvWord = root.findViewById<TextView>(R.id.tv_word)
    private val tvWordTranscription = root.findViewById<TextView>(R.id.tv_word_transcription)
    private val tvWordType = root.findViewById<TextView>(R.id.tv_word_type)
    private val tvWordTranslation = root.findViewById<TextView>(R.id.tv_word_translation)

    private val divider = root.findViewById<View>(R.id.divider)
    private val btnExpand = root.findViewById<ImageButton>(R.id.btn_expand_card)

    private val cardContainer = root.findViewById<ConstraintLayout>(R.id.card_container)
    private val wordBlock = root.findViewById<View>(R.id.word_block)
    private val translationBlock = root.findViewById<View>(R.id.translation_block)

    var card: Card? = null

    init {
        btnExpand.setOnClickListener {
            expandCard()
        }
    }

    // Items might be null if they are not paged in yet
    fun bindTo(card: Card?) {
        resetCard()

        if (card != null) {
            icWordState.setImageResource(card.state.iconResId)
            tvWordState.text = resources.getString(card.state.stringResId)
            tvWord.text = card.word
            tvWordTranscription.text = card.transcription
            tvWordType.text = resources.getString(card.wordType.stringResId)
            tvWordTranslation.text = card.translation.joinToString(", ")
        } else {
            icWordState.setImageResource(R.drawable.ic_status_new_word)
            tvWordState.setText(R.string.loading)
            tvWord.setText(R.string.loading)
            tvWordTranscription.setText(R.string.loading)
            tvWordType.setText(R.string.loading)
            tvWordTranslation.setText(R.string.loading)
        }
    }

    private fun resetCard() {
        divider.visibility = View.GONE
        btnExpand.visibility = View.VISIBLE

        when(type) {
            CardStackAdapter.VIEW_TYPE_DIRECT ->
                translationBlock.visibility = View.GONE

            CardStackAdapter.VIEW_TYPE_REVERSE ->
                wordBlock.visibility = View.GONE
        }
    }

    private fun expandCard() {
        val transition = Fade().apply {
            duration = EXPAND_ANIMATION_DURATION
            addTarget(divider)
            addTarget(translationBlock)
            addTarget(wordBlock)
        }

        TransitionManager.beginDelayedTransition(cardContainer, transition)
        divider.visibility = View.VISIBLE
        btnExpand.visibility = View.GONE

        when (type) {
            CardStackAdapter.VIEW_TYPE_DIRECT ->
                translationBlock.visibility = View.VISIBLE

            CardStackAdapter.VIEW_TYPE_REVERSE ->
                wordBlock.visibility = View.VISIBLE
        }
    }

    companion object {
        const val EXPAND_ANIMATION_DURATION = 250L
    }
}