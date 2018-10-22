package substitution

class Population(val individuals: List<Individual>, val counter: Int = 0) {

    fun childPopulation(): Population {
        val offsprings = List(Population.individuals) {
            val (father, mother) = selectReproduciblePair()
            val child = father crossoverWith mother
            val mutatedChild = child.mutate()
            println("[F] $father")
            println("[M] $mother")
            println("[C] $mutatedChild")
            println("")
            mutatedChild
        }
        return Population(offsprings, counter + 1)
    }

    fun fittest() = individuals.maxBy { it.fitness }!!

    fun selectReproduciblePair(): Pair<Individual, Individual> {
        val first = selectRandomBest(individuals)
        val second = selectRandomBest(individuals.minus(first))

        return Pair(first, second)
    }

    private fun selectRandomBest(individuals: List<Individual>) =
            individuals.shuffled()
                    .take(Population.reproducible)
                    .maxBy { it.fitness }!!

    companion object {
        val individuals = 100
        val reproducible = 30

        fun create() = Population(List(individuals) { Individual.create() })
    }
}

