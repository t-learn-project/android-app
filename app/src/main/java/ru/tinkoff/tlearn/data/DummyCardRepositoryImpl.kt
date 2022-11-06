package ru.tinkoff.tlearn.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.tinkoff.tlearn.domain.entity.Card
import ru.tinkoff.tlearn.domain.entity.CardAction
import ru.tinkoff.tlearn.domain.entity.CardState
import ru.tinkoff.tlearn.domain.entity.WordType
import ru.tinkoff.tlearn.domain.repository.CardRepository

class DummyCardRepositoryImpl : CardRepository {
    private val testCollection = mutableListOf<Card>()

    private val cardList = mutableListOf<Card>()
    private val cardListLD = MutableLiveData<List<Card>>()

    init {
        for (i in 0..30) {
            val card = Card(
                id = i,
                word = "Word #${i + 1}",
                wordType = WordType.NOUN,
                transcription = "/wɜːd/",
                translation = listOf("Слово #${i + 1}"),
                state = CardState.NEW,
                action = null
            )
            testCollection.add(card)
        }
    }

    override fun getCardsToStudy(count: Int): LiveData<List<Card>> {
        cardList.clear()

        var suitableCards = 0

        for (card in testCollection) {
            if (card.state != CardState.LEARNED && card.state != CardState.KNOW) {
                cardList.add(card)
                suitableCards++
            }

            if (suitableCards == count) {
                cardListLD.value = cardList
                return cardListLD
            }
        }

        cardListLD.value = cardList
        return cardListLD
    }

    override fun doCardAction(cardId: Int, action: CardAction) {
        cardList.find { it.id == cardId }?.action = action
    }

    private fun getNextState(cardState: CardState) = when (cardState) {
        CardState.NEW -> CardState.FIVE_MINUTES
        CardState.FIVE_MINUTES -> CardState.ONE_HOUR
        CardState.ONE_HOUR -> CardState.ONE_DAY
        CardState.ONE_DAY -> CardState.ONE_WEEK
        CardState.ONE_WEEK -> CardState.ONE_MONTH
        CardState.ONE_MONTH -> CardState.THREE_MONTHS
        CardState.THREE_MONTHS -> CardState.LEARNED
        CardState.LEARNED -> CardState.LEARNED
        CardState.KNOW -> CardState.KNOW
    }

    override fun syncCardActions() {
        for (card in testCollection) {
            when (card.action) {
                null -> continue
                CardAction.KNOW -> card.state = getNextState(card.state)
                CardAction.DO_NOT_KNOW -> card.state = CardState.NEW
            }
        }
    }
}