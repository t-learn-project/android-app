package ru.tinkoff.tlearn.domain.models

import ru.tinkoff.tlearn.R

enum class WordType(val stringResId: Int) {
    NOUN         (R.string.noun),
    PRONOUN      (R.string.pronoun),
    VERB         (R.string.verb),
    ADJECTIVE    (R.string.adjective),
    ADVERB       (R.string.adverb),
    PREPOSITION  (R.string.preposition),
    CONJUNCTION  (R.string.conjunction),
    INTERJECTION (R.string.interjection),
    EXCLAMATION  (R.string.exclamation)
}