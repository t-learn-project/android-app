package ru.tinkoff.tlearn.domain.entity

data class Card(
    val id: Int,
    val word: String,
    val wordType: WordType,
    val transcription: String,
    val translation: List<String>,
    var state: CardState,
    var action: CardAction?
)