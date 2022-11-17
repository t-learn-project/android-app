package ru.tinkoff.tlearn.domain.repository

import androidx.lifecycle.LiveData
import ru.tinkoff.tlearn.domain.models.CardCollection

interface CollectionRepository {

    fun getActiveCollection(): LiveData<CardCollection>

    fun setActiveCollection(collection: CardCollection)

    fun getAvailableCollections(): LiveData<List<CardCollection>>

}