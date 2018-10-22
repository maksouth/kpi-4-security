package substitution

import java.util.concurrent.ThreadLocalRandom

class Individual(val chromosome: List<Char>) {
    val fitness: Double = Evolution.fitness(key = chromosome)

    infix fun crossoverWith(individual: Individual): Individual {
        val (strongerChromosome, weakerChromosome) =
                if (fitness > individual.fitness)
                    chromosome.toMutableList() to individual.chromosome
                else
                    individual.chromosome.toMutableList() to chromosome

        repeat(strongerChromosome.size) { geneIndex ->
            val crossover = ThreadLocalRandom.current().nextDouble(1.0)

            if (crossover < crossoverFactor) {
                strongerChromosome[strongerChromosome.indexOf(weakerChromosome[geneIndex])] = strongerChromosome[geneIndex]
                strongerChromosome[geneIndex] = weakerChromosome[geneIndex]
            }
        }

        return Individual(strongerChromosome)
    }

    fun mutate(): Individual {
        var nextChromosome = chromosome
        repeat(mutationGenesNumber) {
            nextChromosome = nextChromosome.randomSwapped()
        }

        return Individual(nextChromosome)
    }

    override fun toString() = "{ chromosome = ${chromosome.joinToString("")}, fitness = $fitness}"

    companion object {
        val crossoverFactor = 0.4
        val mutationGenesNumber = 1

        fun create() = Individual(Context.alphabet.shuffled())
    }
}