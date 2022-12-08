package ru.tinkoff.tlearn.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.tinkoff.tlearn.domain.models.Card

interface CardRepository {

    fun getCardsToStudy(): Flow<PagingData<Card>>
}