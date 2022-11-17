package ru.tinkoff.tlearn.di

import dagger.Component
import ru.tinkoff.tlearn.presentation.StudyFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [
    DomainModule::class,
    ContextModule::class,
])
interface AppComponent {

    fun inject(fragment: StudyFragment)

}