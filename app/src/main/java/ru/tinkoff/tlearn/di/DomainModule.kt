package ru.tinkoff.tlearn.di

import dagger.Binds
import dagger.Module
import ru.tinkoff.tlearn.data.CardRepositoryImpl
import ru.tinkoff.tlearn.domain.repository.CardRepository

@Module
interface DomainModule {

    @Binds
    fun bindCardRepository(impl: CardRepositoryImpl): CardRepository
}