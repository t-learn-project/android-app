package ru.tinkoff.tlearn.domain.entity

data class CardCollection(
    val id: Int,
    val title: String,
    val cardCount: Int,
    val active: Boolean
)