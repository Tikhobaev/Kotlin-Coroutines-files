import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis


private const val LONG_WORD_THRESHOLD = 10


fun main() {
    runBlocking {
        val words = getAllWords()
        val longWord = getRandomLongWord(LONG_WORD_THRESHOLD)
        println("Random word: $longWord")
        writeInputWordsToFile(readLine()!!.parseInputWords())
        val inputWords = getMyWords()
        println("Your words: $inputWords")

        val time = measureTimeMillis {
            val score = async { calculateScore(inputWords, words, longWord) }
            println("Счет: ${score.await()}")
        }

        println("Game time: $time")
    }
}


private fun getWordScore(word: String, longWord: String): Int {
    val wordCharMap = word.toCharMap()
    val longWordCharMap = longWord.toCharMap()
    for ((char, count) in wordCharMap) {
        if (longWordCharMap[char] ?: 0 < count) {
            return 0
        }
    }
    return word.length
}


suspend fun calculateScore(
    inputWords: List<String>,
    from: List<String>,
    longWord: String
) = coroutineScope {
    var score = 0
    (inputWords.indices).map { index ->
        async {
            val word = inputWords[index]
            if (from.find { word == it } != null) {
                val symbols = getWordScore(word, longWord)
                println("Word '$word' is found, length = ${word.length}")
                score += symbols
            } else {
                println("Word '$word' is NOT found")
            }
        }
    }.awaitAll()
    score
}
