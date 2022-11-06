package ru.tinkoff.tlearn.domain.repository

import androidx.lifecycle.LiveData
import ru.tinkoff.tlearn.domain.entity.Card
import ru.tinkoff.tlearn.domain.entity.CardAction

interface CardRepository {

    fun getCardsToStudy(count: Int): LiveData<List<Card>>

    fun doCardAction(cardId: Int, action: CardAction)

    fun syncCardActions()

}