import org.junit.Test
import sun.security.util.Length

val originalHexSequence = "1c41023f564b2a130824570e6b47046b521f3f5208201318245e0e6b40022643072e13183e51183f5a1f3e4702245d4b285a1b23561965133f2413192e571e28564b3f5b0e6b50042643072e4b023f4a4b24554b3f5b0238130425564b3c564b3c5a0727131e38564b245d0732131e3b430e39500a38564b27561f3f5619381f4b385c4b3f5b0e6b580e32401b2a500e6b5a186b5c05274a4b79054a6b67046b540e3f131f235a186b5c052e13192254033f130a3e470426521f22500a275f126b4a043e131c225f076b431924510a295f126b5d0e2e574b3f5c4b3e400e6b400426564b385c193f13042d130c2e5d0e3f5a086b52072c5c192247032613433c5b02285b4b3c5c1920560f6b47032e13092e401f6b5f0a38474b32560a391a476b40022646072a470e2f130a255d0e2a5f0225544b24414b2c410a2f5a0e25474b2f56182856053f1d4b185619225c1e385f1267131c395a1f2e13023f13192254033f13052444476b4a043e131c225f076b5d0e2e574b22474b3f5c4b2f56082243032e414b3f5b0e6b5d0e33474b245d0e6b52186b440e275f456b710e2a414b225d4b265a052f1f4b3f5b0e395689cbaa186b5d046b401b2a500e381d61"

//hex to string using https://codebeautify.org/hex-string-converter
val sequence = """A?VK*${'$'}WkGkR?R ${'$'}^k@&C.>Q?Z>G${'$'}]K(Z#Ve?${'$'}.W(VK?[kP&C.K?JK${'$'}UK?[8%VK<VK<Z'8VK${'$'}]2;C9P
8VK'V?V8K8\K?[kX2@*PkZk\'JKyJkgkT?#Zk\."T?
>G&R"P
'_kJ>"_kC${'$'}Q
)_k].WK?\K>@k@&VK8\?-.]?ZkR,\"G&C<[([K<\ VkG.	.@k_
8GK2V
9Gk@&F*G/
%]*_%TK${'$'}AK,A
/Z%GK/V(V?KV"\8_g9Z.?"T?${'$'}DGkJ>"_k].WK"GK?\K/V"C.AK?[k]3GK${'$'}]kRkD'_Ekq*AK"]K&Z/K?[9VËªk]k@*P8a
"""

class TestVigenere {

    @Test
    fun findKeyLength() {
        val shiftToNumber = mutableMapOf<Int, Int>()
        for (shift in 1..sequence.length) {
            for (offset in 4 until sequence.length - shift) {
                if(sequence[offset] == sequence[offset + shift]) {
                    shiftToNumber.put(shift, (shiftToNumber.get(shift) ?: 0) + 1)
                }
            }
        }

        // na glaz, nsd = 3
        shiftToNumber.toList()
                .sortedByDescending { (_, value) -> value }
                .take(20)
                .joinToString( separator = "\n" )
                .let { println(it) }
    }

    @Test fun decrypt() {
        val groups = group(sequence, 3)
        val key = findKey(groups)
        println("KEY $key")
        decrypt(key)
    }

    fun decrypt(key: String) {
        sequence.forEachIndexed { index, c ->
            val charsXor = c.toInt() xor key[index%3].toInt()
            print(charsXor.toChar())
        }
    }

    fun findKey(groups: List<String>): String {
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
}