package ru.tinkoff.tlearn.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.tinkoff.tlearn.domain.models.Card
import ru.tinkoff.tlearn.domain.repository.CardRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CardRepositoryImpl @Inject constructor(
    private val service: MockCardService
): CardRepository {

    override fun getCards(): Flow<PagingData<Card>> {
        return Pager(
            config = PagingConfig(
                pageSize = BATCH_SIZE,
                enablePlaceholders = false,
                maxSize = MAX_CARD_COUNT,
                prefetchDistance = PREFETCH_DISTANCE
            ),
            pagingSourceFactory = {
                CardPagingSource(service)
            }
        ).flow
    }

    companion object {
        const val BATCH_SIZE = 3
        const val MAX_CARD_COUNT = 50
        const val PREFETCH_DISTANCE = 1
    }

}