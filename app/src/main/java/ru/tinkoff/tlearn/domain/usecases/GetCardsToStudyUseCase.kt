package ru.tinkoff.tlearn.domain.usecases

import androidx.lifecycle.LiveData
import ru.tinkoff.tlearn.domain.entity.Card
import ru.tinkoff.tlearn.domain.repository.CardRepository

class GetCardsToStudyUseCase(
    private val repository: CardRepository
) {
    operator fun invoke(): LiveData<List<Card>> {
        return repository.getCardsToStudy(BATCH_SIZE)
    }

    companion object {
        private const val BATCH_SIZE = 10
    }
}