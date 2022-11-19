package ru.tinkoff.tlearn.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.tinkoff.tlearn.domain.models.Card

class CardPagingSource(
    private val service: MockCardService
) : PagingSource<Int, Card>() {

    companion object {
        const val STARTING_PAGE_INDEX = 0
    }

    override fun getRefreshKey(state: PagingState<Int, Card>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Card> {
        val pageIndex = params.key ?: STARTING_PAGE_INDEX
        val offset = pageIndex * params.loadSize
        val cards = service.loadCards(offset, params.loadSize)

        val nextKey =
            if (cards.size < params.loadSize) {
                null
            } else {
                // By default, initial load size = 3 * BATCH_SIZE
                // ensure we're not requesting duplicating items at the 2nd request
                pageIndex + (params.loadSize / CardRepositoryImpl.BATCH_SIZE)
            }

        val prevKey =
            if (pageIndex == STARTING_PAGE_INDEX) {
                null
            } else {
                pageIndex - 1
            }


        return LoadResult.Page(
            data = cards,
            prevKey = prevKey,
            nextKey = nextKey
        )
    }
}