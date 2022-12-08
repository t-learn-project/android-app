package ru.tinkoff.tlearn.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.tinkoff.tlearn.AppDispatchers
import ru.tinkoff.tlearn.domain.models.Card
import ru.tinkoff.tlearn.domain.repository.CardRepository
import javax.inject.Inject

@HiltViewModel
class StudyViewModel @Inject constructor(
    private val repository: CardRepository
): ViewModel() {

    private val _lastCardLock = MutableStateFlow(false)
    val lastCardLock: StateFlow<Boolean>
        get() = _lastCardLock

    val lastCardLocked: Boolean
        get() = _lastCardLock.value

    private val _noMoreCards = MutableStateFlow(false)
    val noMoreCards: StateFlow<Boolean>
        get() = _noMoreCards

    fun getCards(): Flow<PagingData<Card>> {
        return repository.getCardsToStudy().cachedIn(viewModelScope)
    }

    fun endOfPaginationReached() {
        _noMoreCards.value = true
    }

    fun lockLastCard() {
        _lastCardLock.value = true
    }

    fun unlockLastCard() {
        _lastCardLock.value = false
    }

    fun positiveCardAction() {
    }

    fun negativeCardAction() {
    }

}