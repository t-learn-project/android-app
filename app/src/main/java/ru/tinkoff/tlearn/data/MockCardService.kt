package ru.tinkoff.tlearn.data


import ru.tinkoff.tlearn.domain.models.Card
import ru.tinkoff.tlearn.domain.models.CardState
import ru.tinkoff.tlearn.domain.models.WordType
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random

@Singleton
class MockCardService @Inject constructor() {
    private val testCollection = mutableListOf<Card>()
    private var currentChunk = mutableListOf<Card>()

    init {
        testCollection.addAll(
            listOf(
                Card(
                    id = 0,
                    word = "hello",
                    wordType = WordType.NOUN,
                    transcription = "[helˈəʊ]",
                    translation = listOf("привет", "здравствуйте"),
                    state = CardState.NEW,
                    reversed = Random.nextBoolean(),
                    action = null
                ),
                Card(
                    id = 1,
                    word = "bye",
                    wordType = WordType.NOUN,
                    transcription = "[baɪ]",
                    translation = listOf("пока", "до свидания"),
                    state = CardState.FIVE_MINUTES,
                    reversed = Random.nextBoolean(),
                    action = null
                ),
                Card(
                    id = 2,
                    word = "tea",
                    wordType = WordType.NOUN,
                    transcription = "[tiː]",
                    translation = listOf("чай"),
                    state = CardState.NEW,
                    reversed = Random.nextBoolean(),
                    action = null
                ),
                Card(
                    id = 3,
                    word = "air-conditioned",
                    wordType = WordType.ADJECTIVE,
                    transcription = "[ˈeəkənˌdɪʃənd]",
                    translation = listOf("с кондиционером", "охлажденный"),
                    state = CardState.NEW,
                    reversed = Random.nextBoolean(),
                    action = null
                ),
                Card(
                    id = 4,
                    word = "wassup",
                    wordType = WordType.EXCLAMATION,
                    transcription = "[ˌwɒsˈʌp]",
                    translation = listOf("как дела", "чё каво", "как житуха"),
                    state = CardState.ONE_WEEK,
                    reversed = Random.nextBoolean(),
                    action = null
                ),
                Card(
                    id = 5,
                    word = "steady",
                    wordType = WordType.ADJECTIVE,
                    transcription = "[ˈsted.i]",
                    translation = listOf("постоянный", "стабильный"),
                    state = CardState.ONE_WEEK,
                    reversed = Random.nextBoolean(),
                    action = null
                ),
                Card(
                    id = 6,
                    word = "official",
                    wordType = WordType.ADJECTIVE,
                    transcription = "[əˈfɪʃ.əl]",
                    translation = listOf("официальный"),
                    state = CardState.ONE_MONTH,
                    reversed = Random.nextBoolean(),
                    action = null
                ),
                Card(
                    id = 7,
                    word = "unprofessional",
                    wordType = WordType.ADJECTIVE,
                    transcription = "[ˌʌn.prəˈfeʃ.ən.əl]",
                    translation = listOf("непрофессиональный"),
                    state = CardState.THREE_MONTHS,
                    reversed = Random.nextBoolean(),
                    action = null
                ),
                Card(
                    id = 8,
                    word = "takeover",
                    wordType = WordType.NOUN,
                    transcription = "[ˈteɪkˌəʊ.vər]",
                    translation = listOf("поглощение", "захват"),
                    state = CardState.NEW,
                    reversed = Random.nextBoolean(),
                    action = null
                ),
                Card(
                    id = 9,
                    word = "labour",
                    wordType = WordType.NOUN,
                    transcription = "[ˈleɪ.bər]",
                    translation = listOf("труд", "трудовые ресурсы"),
                    state = CardState.NEW,
                    reversed = Random.nextBoolean(),
                    action = null
                ),
                Card(
                    id = 10,
                    word = "venture",
                    wordType = WordType.NOUN,
                    transcription = "[ˈven.tʃər]",
                    translation = listOf("венчур", "предприятие"),
                    state = CardState.NEW,
                    reversed = Random.nextBoolean(),
                    action = null
                )
            )
        )

        for (i in 11..50) {
            testCollection.add(
                Card(
                    id = i,
                    word = "word $i",
                    wordType = WordType.NOUN,
                    transcription = "[word $i]",
                    translation = listOf("слово $i"),
                    state = CardState.NEW,
                    reversed = Random.nextBoolean(),
                    action = null
                )
            )
        }
    }

    fun loadCards(offset: Int, count: Int): List<Card> {
        currentChunk.clear()

        for (i in offset until offset + count) {
            if (i < testCollection.size)
                currentChunk.add(testCollection[i])
        }

        return currentChunk
    }

//    fun doCardAction(cardId: Int, action: CardAction) {
//        cardList.find { it.id == cardId }?.action = action
//    }

    private fun getNextState(cardState: CardState) = when (cardState) {
        CardState.NEW          -> CardState.FIVE_MINUTES
        CardState.FIVE_MINUTES -> CardState.ONE_HOUR
        CardState.ONE_HOUR     -> CardState.ONE_DAY
        CardState.ONE_DAY      -> CardState.ONE_WEEK
        CardState.ONE_WEEK     -> CardState.ONE_MONTH
        CardState.ONE_MONTH    -> CardState.THREE_MONTHS
        CardState.THREE_MONTHS -> CardState.LEARNED
        CardState.LEARNED      -> CardState.LEARNED
        CardState.KNOWN        -> CardState.KNOWN
    }


//    fun syncCardActions() {
//        for (i in offset until cardList.size) {
//            val card = cardList[i]
//
//            when (card.action) {
//                null -> continue
//                CardAction.KNOW -> card.state = getNextState(card.state)
//                CardAction.DO_NOT_KNOW -> card.state = CardState.NEW
//            }
//        }
//
//        offset += suitableCards
//    }
//
//    private fun updateLiveData() {
//        cardListLD.value = cardList
//    }
}