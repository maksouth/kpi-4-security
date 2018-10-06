import org.junit.Test
import java.io.BufferedReader
import java.io.File
import java.io.FileReader

class EncryptionKtTest {


    val total = getFileStream().flatMap { it.asSequence() }.count()
    val frequencies = getFileStream().flatMap { it.asSequence() }
            .groupingBy { it }
            .eachCount()
            .toList()
            .sortedByDescending { (_, freq) -> freq }
            .toMap()

    val charsList = getChars().toList()

    val englishHighStrings = listOf("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z")
    val englishLowStrings = listOf("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z")
    val numbersStrings = listOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9")

    val numbers = listOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9')
    val englishHigh = listOf('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z')
    val englishLow = listOf('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z')

    val securityKey = listOf('S', 'E', 'C', 'U', 'R', 'I', 'T', 'Y')

    val allSymbolToIndex: Map<Char, Int> = (englishHigh + englishLow + numbers)
            .toSet()
            .mapIndexed { index, char -> char to index }.toMap()

    val allIndexToSymbol = allSymbolToIndex
            .map { (char, index) -> index to char }
            .toMap()

    @Test
    fun showFrequencyForEachLetter() =
            frequencies.forEach { char, freq
                ->
                System.out.printf("$char  %-4d %5.4f\n", freq, freq.toDouble() / total)
            }

    @Test
    fun showAllChars() = println("\nSET: ${frequencies.keys.sorted().joinToString(separator = """", """")}")

    @Test
    fun showCharSetNumber() = println("CHARS NUMBER ${frequencies.keys.size}")

    @Test
    fun showTotal() = println("\nTOTAL $total")

    @Test
    fun printIndexedCharMap() = print(allSymbolToIndex.toList().joinToString(separator = "\n"))

    @Test
    fun testCaesarCipher() {
        (0 until allIndexToSymbol.size).forEach {
            val i = it
            print("$i: ")
            getChars()
//                    .onEach { print("$it -> ") }
                    .map { allSymbolToIndex[it]!! }
//                    .onEach { print("$it -> ") }
                    .map { (it + i) % allSymbolToIndex.size }
//                    .onEach { print("$it -> ") }
                    .map { allIndexToSymbol[it]!! }
                    .forEach(::print)
            println()
        }
    }

    @Test fun testFindVigenereCipherKeyLength() {
        val shiftToCoincidence: MutableMap<Int, Int> = mutableMapOf()
        val textLine = getChars().joinToString(separator = "")

        for (i in 1..textLine.length)
            for (j in 0 until (textLine.length - i))
                if (textLine[j] == textLine[i + j])
                    shiftToCoincidence.compute(i) {_, value ->
                        val result = value?.inc() ?: 1
                        println(" ${textLine[j]} == ${textLine[j + i]} $value -> $result")
                        result
                    }

        shiftToCoincidence.toList()
                .sortedByDescending { (_, value) -> value }
                .joinToString( separator = "\n" )
                .let { println(it) }
    }

    val englishSymbolFrequency = mapOf('E' to 12.02, 'T' to 9.10, 'A' to 8.12, 'O' to 7.68, 'I' to 7.31,
            'N' to 6.95, 'S' to 6.28, 'R' to 6.02, 'H' to 5.92, 'D' to 4.32,
            'L' to 3.98, 'U' to 2.88, 'C' to 2.71, 'M' to 2.61, 'F' to 2.30,
            'Y' to 2.11, 'W' to 2.09, 'G' to 2.03, 'P' to 1.82, 'B' to 1.49,
            'V' to 1.11, 'K' to 0.69, 'X' to 0.17, 'Q' to 0.11, 'J' to 0.10,
            'Z' to 0.07)
            .toSortedMap()

    @Test fun testFindVigenereCipherKey() {
        val keyLength = 16

        (0 until keyLength)
                .onEach { print("padding $it ")}
                .map { charsList.takeNth(keyLength, it) }
                .map { listOfNth ->
                    listOfNth.groupingBy { it }
                            .eachCount()
                            .map { (key, value) -> key to value.toDouble() / listOfNth.size }
                            .toMap()
                            .toSortedMap()
                }
                .forEach {
                    println(it)

                    //for (shift in )

                }
    }

    fun <T> List<T>.takeNth(base: Int, shift: Int) =
        (0 until size).filter { it % base == shift }
                .map { get(it) }
                .toList()

    @Test fun testSubstitution() {
        val substitution = mapOf('V' to 'E', 'U' to 'T', 'R' to 'A', 'T' to 'O', 'W' to 'I', 'Z' to 'N', 'l' to 'S', 'F' to 'R', 'k' to 'H', '1' to 'D')
        getChars().toString().toUpperCase().asSequence()
                .map { substitution[it] ?: it }
                .forEach(::print)
        println()
    }

    @Test
    fun sandBox() {
        println((englishHigh + englishLow + numbers).joinToString(separator = ""))
    }
}

fun getChars(): Sequence<Char> =
        getFileStream().flatMap { it.asSequence() }

fun getFileStream(): Sequence<String> {
    val classLoader = EncryptionKtTest::class.java.classLoader
    val file = File(classLoader.getResource("encrypted.txt").file)
    val reader = BufferedReader(FileReader(file))
    return reader.lineSequence()
}

