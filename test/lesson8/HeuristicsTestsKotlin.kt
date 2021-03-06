package lesson8

import org.junit.jupiter.api.Tag
import kotlin.test.Test

class HeuristicsTestsKotlin : AbstractHeuristicsTests() {

    @Test
    @Tag("12")
    fun testFillKnapsackCompareWithGreedyTest() {
        fillKnapsackCompareWithGreedyTest { load, items ->
            fillKnapsackHeuristics(
                load, items, 100,
                50
            )
        }
    }

    @Test
    @Tag("12")
    fun testFindVoyagingPathHeuristics() {
        findVoyagingPathHeuristics { findVoyagingPathHeuristics() }
    }
}