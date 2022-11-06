package ru.tinkoff.tlearn.domain.usecases

import ru.tinkoff.tlearn.domain.entity.CardAction
import ru.tinkoff.tlearn.domain.repository.CardRepository

class DoCardActionUseCase(
    private val repository: CardRepository
) {
    operator fun invoke(cardId: Int, action: CardAction) {
        repository.doCardAction(cardId, action)
    }
}