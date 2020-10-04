package lesson8.genetic

import java.util.*

internal class KnapsackChromosome(val gen: List<Int>) {
    constructor(size: Int, random: Random) : this(
        (0 until size).toMutableList().apply { shuffle(random) }
    )

    private fun mutate(random: Random): KnapsackChromosome =
        KnapsackChromosome(
            gen.toMutableList().apply {
                val first = random.nextInt(gen.size)
                var second: Int
                do {
                    second = random.nextInt(gen.size)
                } while (first == second)
                Collections.swap(this, first, second)
            }
        )

    fun mutateWithChance(random: Random, mutationChance: Double) =
        if (random.nextDouble() < mutationChance) this.mutate(random) else this

    fun crossBreed(other: KnapsackChromosome, random: Random): KnapsackChromosome {
        assert(gen.size == other.gen.size)
        val res = mutableListOf<Int>()
        for (i in this.gen.indices)
            res.add(
                if (gen[i] == other.gen[i]) gen[i]
                else random.nextInt(gen.size)
            )
        assert(gen.size == res.size)
        return KnapsackChromosome(res)
    }
}