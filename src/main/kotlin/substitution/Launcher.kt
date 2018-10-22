package substitution

fun main(args: Array<String>) {
    var population = Population.create()

    // -160 was found experimentally and condition was modified after key was found
    while (population.fittest().fitness < -160) {
        population = population.childPopulation()

        println("[P ${population.counter}] Fittest ${population.fittest()}")
    }

    println("SOLVED: ${population.fittest()}")

    println(decryptTextWithKey(Context.text, population.fittest().chromosome))
}