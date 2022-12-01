package ru.tinkoff.tlearn.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.tinkoff.tlearn.data.CardRepositoryImpl
import ru.tinkoff.tlearn.domain.repository.CardRepository
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface DomainModule {

    @Binds
    @Singleton
    fun bindCardRepository(impl: CardRepositoryImpl): CardRepository
}