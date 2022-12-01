package ru.tinkoff.tlearn.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import ru.tinkoff.tlearn.AppDispatchers
import ru.tinkoff.tlearn.domain.models.Card
import ru.tinkoff.tlearn.domain.repository.CardRepository
import javax.inject.Inject

@HiltViewModel
class StudyViewModel @Inject constructor(
    private val dispatchers: AppDispatchers,
    private val repository: CardRepository
): ViewModel() {


    fun getCards(): Flow<PagingData<Card>> {
        return repository.getCards().cachedIn(viewModelScope)
    }

}