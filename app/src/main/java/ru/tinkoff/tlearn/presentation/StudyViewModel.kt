package ru.tinkoff.tlearn.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import ru.tinkoff.tlearn.domain.models.Card
import ru.tinkoff.tlearn.domain.repository.CardRepository

class StudyViewModel(
    private val repository: CardRepository
): ViewModel() {


    fun getCards(): Flow<PagingData<Card>> {
        return repository.getCards().cachedIn(viewModelScope)
    }


}