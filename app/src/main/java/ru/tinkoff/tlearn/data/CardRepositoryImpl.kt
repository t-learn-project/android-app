package ru.tinkoff.tlearn.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.tinkoff.tlearn.domain.models.Card
import ru.tinkoff.tlearn.domain.repository.CardRepository
import javax.inject.Inject
import javax.inject.Singleton


class CardRepositoryImpl @Inject constructor(
    private val cardPagingSource: CardPagingSource
): CardRepository {

    companion object {
        const val BATCH_SIZE = 10
        const val PREFETCH_DISTANCE = 1
        const val INITIAL_LOAD_SIZE = 10
    }

    override fun getCards(): Flow<PagingData<Card>> {
        return Pager(
            config = PagingConfig(
                pageSize = BATCH_SIZE,
                enablePlaceholders = false,
                prefetchDistance = PREFETCH_DISTANCE,
                initialLoadSize = INITIAL_LOAD_SIZE
            ),
            pagingSourceFactory = {
                cardPagingSource
            }
        ).flow
    }

}