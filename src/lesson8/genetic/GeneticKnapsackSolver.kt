package lesson8.genetic

import lesson7.knapsack.Fill
import lesson7.knapsack.Item
import lesson8.AbstractKnapsackSolver

class GeneticKnapsackSolver(
    private val load: Int,
    private val items: List<Item>,
    private val chromosomeNumber: Int,
    private val generationNumber: Int
) : AbstractKnapsackSolver(items) {

    private fun generateChromosomes(number: Int): List<KnapsackChromosome> {
        val res = mutableListOf<KnapsackChromosome>()
        for (i in 0 until number)
            res.add(KnapsackChromosome(size, random))
        return res
    }

    private fun List<KnapsackChromosome>.generateCrossBreeds(): List<KnapsackChromosome> {
        val res = mutableListOf<KnapsackChromosome>()
        for (i in 0 until size) {
            val first = this[random.nextInt(size)]
            val second = this[random.nextInt(size)]
            res.add(
                if (first == second) first else first.crossBreed(second, random)
            )
        }
        return res
    }

    private fun KnapsackChromosome.evaluation(): Int {
        var tempLoad = 0
        var result = 0
        for (i in gen.indices) {
            if (this.gen[i] == 1) {
                tempLoad += items[i].weight
                result += items[i].cost
            }
        }
        if (tempLoad > load) {
            result -= tempLoad - load
        }
        return result
    }

    override fun findBestFill(): Fill {
        var chromosomes = generateChromosomes(chromosomeNumber)
        for (generation in 0 until generationNumber) {
            val crossBreeds = chromosomes.generateCrossBreeds()
            val crossBreedsAfterMutation =
                crossBreeds.map { it.mutateWithChance(random, 0.1) }
            val evaluatedChromosomes =
                (chromosomes + crossBreedsAfterMutation).sortedByDescending { it.evaluation() }
            chromosomes = evaluatedChromosomes.subList(0, chromosomeNumber)
        }
        val chromosome = chromosomes.first()
        val resSet = mutableSetOf<Item>()

        for (i in chromosome.gen.indices)
            if (chromosome.gen[i] == 1) resSet.add(items[i])

        return Fill(chromosome.evaluation(), resSet)
    }
}