package ru.tinkoff.tlearn.domain.usecases

import ru.tinkoff.tlearn.domain.repository.CardRepository

class SyncCardActionsUseCase(
    private val repository: CardRepository
) {
    operator fun invoke() {
        repository.syncCardActions()
    }
}