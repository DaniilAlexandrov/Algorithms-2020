@file:Suppress("UNUSED_PARAMETER", "unused")

package lesson6

import lesson6.impl.GraphBuilder

typealias Vertex = Graph.Vertex
typealias Edge = Graph.Edge

/**
 * Эйлеров цикл.
 * Средняя
 *
 * Дан граф (получатель). Найти по нему любой Эйлеров цикл.
 * Если в графе нет Эйлеровых циклов, вернуть пустой список.
 * Соседние дуги в списке-результате должны быть инцидентны друг другу,
 * а первая дуга в списке инцидентна последней.
 * Длина списка, если он не пуст, должна быть равна количеству дуг в графе.
 * Веса дуг никак не учитываются.
 *
 * Пример:
 *
 *      G -- H
 *      |    |
 * A -- B -- C -- D
 * |    |    |    |
 * E    F -- I    |
 * |              |
 * J ------------ K
 *
 * Вариант ответа: A, E, J, K, D, C, H, G, B, C, I, F, B, A
 *
 * Справка: Эйлеров цикл -- это цикл, проходящий через все рёбра
 * связного графа ровно по одному разу
 */
fun Graph.findEulerLoop(): List<Graph.Edge> {
    TODO()
}

/**
 * Минимальное остовное дерево.
 * Средняя
 *
 * Дан связный граф (получатель). Найти по нему минимальное остовное дерево.
 * Если есть несколько минимальных остовных деревьев с одинаковым числом дуг,
 * вернуть любое из них. Веса дуг не учитывать.
 *
 * Пример:
 *
 *      G -- H
 *      |    |
 * A -- B -- C -- D
 * |    |    |    |
 * E    F -- I    |
 * |              |
 * J ------------ K
 *
 * Ответ:
 *
 *      G    H
 *      |    |
 * A -- B -- C -- D
 * |    |    |
 * E    F    I
 * |
 * J ------------ K
 * Трудоемкость - О(Vertex * Edge)
 * Ресурсоемкость - O(Vertex + Edge)
 */
fun Graph.minimumSpanningTree(): Graph {
    val builder = GraphBuilder()
    if (vertices.isEmpty()) return builder.build()
    val randomVertex = vertices.first()
    val vertexSet = mutableSetOf(randomVertex)
    val edgeSet = mutableSetOf<Edge>()

    fun connect(vertex: Vertex) {
        for ((ver, edge) in getConnections(vertex)) {
            if (!vertexSet.contains(ver)) {
                vertexSet.add(ver)
                edgeSet.add(edge)
                connect(ver)
            }
        }
    }
    connect(randomVertex)
    return builder.apply {
        addVertex(randomVertex.name)
        if (edgeSet.first().begin == randomVertex)
            for (edge in edgeSet) {
                addVertex(edge.end.name)
                addConnection(edge)
            }
        else
            for (edge in edgeSet) {
                addVertex(edge.begin.name)
                addConnection(edge)
            }
    }.build()

}

/**
 * Максимальное независимое множество вершин в графе без циклов.
 * Сложная
 *
 * Дан граф без циклов (получатель), например
 *
 *      G -- H -- J
 *      |
 * A -- B -- D
 * |         |
 * C -- F    I
 * |
 * E
 *
 * Найти в нём самое большое независимое множество вершин и вернуть его.
 * Никакая пара вершин в независимом множестве не должна быть связана ребром.
 *
 * Если самых больших множеств несколько, приоритет имеет то из них,
 * в котором вершины расположены раньше во множестве this.vertices (начиная с первых).
 *
 * В данном случае ответ (A, E, F, D, G, J)
 *
 * Если на входе граф с циклами, бросить IllegalArgumentException
 *
 * Эта задача может быть зачтена за пятый и шестой урок одновременно
 * Трудоемкость - O(Vertex * Edge)
 * Ресурсоемкость - O(Vertex)
 */
fun Graph.largestIndependentVertexSet(): Set<Graph.Vertex> {
    if (vertices.isEmpty()) return emptySet()

    val remaining = vertices
    val trees = mutableSetOf<Vertex>()

    fun createTree() {
        val tree = remaining.first()
        trees.add(tree)
        remaining.remove(tree)
        var neighbours = getNeighbors(tree).filter { remaining.contains(it) }
        while (neighbours.isNotEmpty()) {
            val newNeighbours = mutableListOf<Vertex>()
            neighbours.forEach {
                newNeighbours += getNeighbors(it).filter { vertex -> remaining.contains(vertex) }
                remaining.remove(it)
            }
            neighbours = newNeighbours
        }
    }

    while (remaining.isNotEmpty())
        createTree()

    fun Set<Vertex>.getLargestSet(other: Set<Vertex>): Set<Vertex> {
        when {
            this.size > other.size -> return this
            this.size < other.size -> return other
            else -> vertices.forEach {
                if (this.contains(it)) return this
                if (other.contains(it)) return other
            }
        }
        return this
    }

    val sets = mutableMapOf<Vertex, Set<Vertex>>()

    fun createSet(vertex: Vertex, previous: Set<Vertex>): Set<Vertex> {
        if (sets[vertex] != null) return sets.getValue(vertex)
        val children = mutableSetOf<Vertex>()
        val grandChildren = mutableSetOf<Vertex>()
        val tempList = mutableSetOf<Vertex>()
        getNeighbors(vertex).filter { !previous.contains(it) }.forEach {
            tempList += getNeighbors(it)
            tempList.remove(vertex)
            children += createSet(it, previous + vertex)
        }
        tempList.filter { !previous.contains(it) }.forEach {
            grandChildren += createSet(it, previous + vertex)
        }
        grandChildren.add(vertex)
        sets[vertex] = children.getLargestSet(grandChildren)
        return sets.getValue(vertex)
    }

    val sum = mutableSetOf<Vertex>()
    trees.forEach {
        sum += createSet(it, setOf(it))
    }
    return sum
}

/**
 * Наидлиннейший простой путь.
 * Сложная
 *
 * Дан граф (получатель). Найти в нём простой путь, включающий максимальное количество рёбер.
 * Простым считается путь, вершины в котором не повторяются.
 * Если таких путей несколько, вернуть любой из них.
 *
 * Пример:
 *
 *      G -- H
 *      |    |
 * A -- B -- C -- D
 * |    |    |    |
 * E    F -- I    |
 * |              |
 * J ------------ K
 *
 * Ответ: A, E, J, K, D, C, H, G, B, F, I
 */
fun Graph.longestSimplePath(): Path {
    TODO()
}

/**
 * Балда
 * Сложная
 *
 * Задача хоть и не использует граф напрямую, но решение базируется на тех же алгоритмах -
 * поэтому задача присутствует в этом разделе
 *
 * В файле с именем inputName задана матрица из букв в следующем формате
 * (отдельные буквы в ряду разделены пробелами):
 *
 * И Т Ы Н
 * К Р А Н
 * А К В А
 *
 * В аргументе words содержится множество слов для поиска, например,
 * ТРАВА, КРАН, АКВА, НАРТЫ, РАК.
 *
 * Попытаться найти каждое из слов в матрице букв, используя правила игры БАЛДА,
 * и вернуть множество найденных слов. В данном случае:
 * ТРАВА, КРАН, АКВА, НАРТЫ
 *
 * И т Ы Н     И т ы Н
 * К р а Н     К р а н
 * А К в а     А К В А
 *
 * Все слова и буквы -- русские или английские, прописные.
 * В файле буквы разделены пробелами, строки -- переносами строк.
 * Остальные символы ни в файле, ни в словах не допускаются.
 */
fun baldaSearcher(inputName: String, words: Set<String>): Set<String> {
    TODO()
}