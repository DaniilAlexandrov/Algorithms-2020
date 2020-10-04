package lesson8

import lesson7.knapsack.Fill
import lesson7.knapsack.Item
import java.util.*

abstract class AbstractKnapsackSolver(items: List<Item>) {

    protected val size = items.size
    protected val random = Random()

    abstract fun findBestFill(): Fill

}