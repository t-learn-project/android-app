package ru.tinkoff.tlearn.domain.models

import ru.tinkoff.tlearn.R

enum class CardState(
    val stringResId: Int,
    val iconResId: Int
) {
    KNOWN        (R.string.known_word, R.drawable.ic_status_memorized_word),
    NEW          (R.string.new_word, R.drawable.ic_status_new_word),
    FIVE_MINUTES (R.string.review_five_minutes, R.drawable.ic_status_review_word),
    ONE_HOUR     (R.string.review_one_hour, R.drawable.ic_status_review_word),
    ONE_DAY      (R.string.review_one_day, R.drawable.ic_status_review_word),
    ONE_WEEK     (R.string.review_one_week, R.drawable.ic_status_review_word),
    ONE_MONTH    (R.string.review_one_month, R.drawable.ic_status_review_word),
    THREE_MONTHS (R.string.review_three_months, R.drawable.ic_status_review_word),
    LEARNED      (R.string.learned_word, R.drawable.ic_status_memorized_word)
}