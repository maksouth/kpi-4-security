package vigenere

class KeyFinder {

    fun key(text: String): String {
        val keyLength = findKeyLength(text)
        val groups = group(text, keyLength)
        val key = findKey(groups)
        return key
    }

    private fun findKeyLength(text: String): Int {
        val shiftToNumber = mutableMapOf<Int, Int>()
        for (shift in 1..text.length) {
            for (offset in 4 until text.length - shift) {
                if(text[offset] == text[offset + shift]) {
                    shiftToNumber.put(shift, (shiftToNumber.get(shift) ?: 0) + 1)
                }
            }
        }

        // na glaz, nsd = 3, ya zabuv jak rachuvaty nsd, i czy varto tut take robyty
        shiftToNumber.toList()
                .sortedByDescending { (_, value) -> value }
                .take(20)
                .joinToString( separator = "\n" )
                .let { println(it) }

        return 3
    }

    private fun findKey(groups: List<String>): String {
        val space = ' '
        val key = StringBuilder("")

        groups.forEach { group ->
            val xoredGroup = group.map { it.toInt() }
                    .map { it xor space.toInt() }
                    .map { it.toChar() }
                    .joinToString(separator = "")
            val mostUsed = mostUsedChar(xoredGroup)
            key.append(mostUsed)
        }

        return key.toString()
    }
}

class Decryptor {
    fun decryptAndPrint(text: String, key: String) {
        text.forEachIndexed { index, c ->
            val charsXor = c.toInt() xor key[index % key.length].toInt()
            print(charsXor.toChar())
        }
    }
}

fun mostUsedChar(string: String) =
    string.groupingBy { it }
            .eachCount()
            .maxBy { (_, value) -> value }!!
            .key

fun group(text: String, keyLength: Int): List<String> {
    val groups = mutableListOf<String>()
    repeat(keyLength) { groups.add("") }

    text.forEachIndexed { index, c ->
        val position = index % keyLength
        groups[position] += c.toString()
    }

    return groups
}