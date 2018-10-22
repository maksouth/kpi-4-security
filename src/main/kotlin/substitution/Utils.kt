package substitution

import java.util.concurrent.ThreadLocalRandom
import kotlin.math.log10

fun decryptTextWithKey(text: List<Char>, key: List<Char>): String {
    val textCopy = text.toMutableList()
    for (i in 0 until textCopy.size) {
        textCopy[i] = Context.alphabet[ key.indexOf(textCopy[i]) ]
    }

    return textCopy.joinToString(separator = "")
}

fun logProbability(quadgram: String): Double {
    val count = Context.quadgrams[quadgram]?.toDouble() ?: 0.01
    return log10(count / Context.quadgrams.size)
}

fun List<Char>.randomSwapped(): List<Char> {
    val result = toMutableList()
    val first = ThreadLocalRandom.current().nextInt(0, size)
    val second = ThreadLocalRandom.current().nextInt(0, size)

    val temp = result[first]
    result[first] = result[second]
    result[second] = temp

    return result
}