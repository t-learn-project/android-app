package ru.tinkoff.tlearn.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.tinkoff.tlearn.domain.repository.CardRepository
import javax.inject.Inject

class StudyViewModelFactory @Inject constructor(
    private val repository: CardRepository
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StudyViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST") // Guaranteed to succeed at this point.
            return StudyViewModel(repository) as T
        } else {
            throw RuntimeException("Unknown view model class: $modelClass")
        }
    }
}