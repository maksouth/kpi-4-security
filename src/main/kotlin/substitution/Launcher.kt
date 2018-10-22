package substitution

fun main(args: Array<String>) {
    var population = Population.create()

    while (population.fittest().fitness < -150) {
        population = population.childPopulation()

        println("[P ${population.counter}] Fittest ${population.fittest()}")
    }

    println("SOLVED: ${population.fittest()}")

    println(decryptTextWithKey(Context.text, "EKMFLGDQVJNTOWYHXUSPAIBRCZ".toList()))
}