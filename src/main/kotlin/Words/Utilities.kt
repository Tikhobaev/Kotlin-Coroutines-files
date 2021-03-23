import java.io.File
import kotlin.random.Random


private const val RESULT_FILENAME = "my_words.txt"


fun getAllWords(): List<String> {
    val bufferedReader = File("words.txt").bufferedReader()
    return bufferedReader.use { it.readLines() }.map { it.toLowerCase() }
}


fun getRandomLongWord(threshold: Int): String {
    val longWords = getAllWords().filter { it.length >= threshold }
    return longWords[Random.nextInt(0, longWords.size)]
}


fun writeInputWordsToFile(words: List<String>) {
    val file = File(RESULT_FILENAME)
    file.printWriter().use { out ->
        for (word in words) {
            out.println(word)
        }
    }
}


fun getMyWords(): List<String> {
    val bufferedReader = File(RESULT_FILENAME).bufferedReader()
    return bufferedReader.use { it.readLines() }.map { it.toLowerCase() }
}

fun String.parseInputWords(): List<String> {
    return this.split(" ", ignoreCase = true).map {
        it.toLowerCase().trim()
    }
}


fun String.toCharMap(): Map<Char, Int> {
    val mapOfChar = mutableMapOf<Char, Int>()
    for (char in this) {
        mapOfChar[char] = mapOfChar[char]?.plus(1) ?: 1
    }
    return mapOfChar
}
