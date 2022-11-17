package ru.tinkoff.tlearn.domain.models

data class Card(
    val id: Int,
    val word: String,
    val wordType: WordType,
    val transcription: String,
    val translation: List<String>,
    val reversed: Boolean = false,
    var state: CardState,
    var action: CardAction?
)