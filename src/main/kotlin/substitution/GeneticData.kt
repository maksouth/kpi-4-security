package substitution

import java.io.File

object Evolution {

    fun fitness(text: List<Char> = Context.text, key: List<Char>): Double {
        val decryptedText = decryptTextWithKey(text, key)

        var fitness = 0.0
        for (i in 0 until decryptedText.length - 4) {
            val quadgram = decryptedText.substring(i, i + 4)
            fitness += logProbability(quadgram)
        }

        return fitness
    }

}

object Context {
    val alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toList()
    val text = "EFFPQLEKVTVPCPYFLMVHQLUEWCNVWFYGHYTCETHQEKLPVMSAKSPVPAPVYWMVHQLUSPQLYWLASLFVWPQLMVHQLUPLRPSQLULQESPBLWPCSVRVWFLHLWFLWPUEWFYOTCMQYSLWOYWYETHQEKLPVMSAKSPVPAPVYWHEPPLUWSGYULEMQTLPPLUGUYOLWDTVSQETHQEKLPVPVSMTLEUPQEPCYAMEWWYOYULULTCYWPQLSEOLSVOHTLUYAPVWLYGDALSSVWDPQLNLCKCLRQEASPVILSLEUMQBQVMQCYAHUYKEKTCASLFPYFLMVHQLUHULIVYASHEUEDUEHQBVTTPQLVWFLRYGMYVWMVFLWMLSPVTTBYUNESESADDLSPVYWCYAMEWPUCPYFVIVFLPQLOLSSEDLVWHEUPSKCPQLWAOKLUYGMQEUEMPLUSVWENLCEWFEHHTCGULXALWMCEWETCSVSPYLEMQYGPQLOMEWCYAGVWFEBECPYASLQVDQLUYUFLUGULXALWMCSPEPVSPVMSBVPQPQVSPCHLYGMVHQLUPQLWLRPHEUEDUEHQMYWPEVWSSYOLHULPPCVWPLULSPVWDVWGYUOEPVYWEKYAPSYOLEFFVPVYWETULBEUF".toList()
    val quadgrams: Map<String, Int> by lazy(Context::loadDictionary)

    private fun loadDictionary(): Map<String, Int> {
        val quadgrams = mutableMapOf<String, Int>()
        val file = File(javaClass.classLoader.getResource("english_quadgrams.txt").file)

        file.useLines { lines ->
            lines.map { it.split(delimiters = *arrayOf(" ")).take(2) }
                    .map { it[0] to it[1].toInt() }
                    .forEach { quadgrams += it }
        }

        return quadgrams
    }
}

