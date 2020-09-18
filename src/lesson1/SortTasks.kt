@file:Suppress("UNUSED_PARAMETER")

package lesson1

import java.io.File

/**
 * Сортировка времён
 *
 * Простая
 * (Модифицированная задача с сайта acmp.ru)
 *
 * Во входном файле с именем inputName содержатся моменты времени в формате ЧЧ:ММ:СС AM/PM,
 * каждый на отдельной строке. См. статью википедии "12-часовой формат времени".
 *
 * Пример:
 *
 * 01:15:19 PM
 * 07:26:57 AM
 * 10:00:03 AM
 * 07:56:14 PM
 * 01:15:19 PM
 * 12:40:31 AM
 *
 * Отсортировать моменты времени по возрастанию и вывести их в выходной файл с именем outputName,
 * сохраняя формат ЧЧ:ММ:СС AM/PM. Одинаковые моменты времени выводить друг за другом. Пример:
 *
 * 12:40:31 AM
 * 07:26:57 AM
 * 10:00:03 AM
 * 01:15:19 PM
 * 01:15:19 PM
 * 07:56:14 PM
 *
 * В случае обнаружения неверного формата файла бросить любое исключение.
 */
fun sortTimes(inputName: String, outputName: String) {
    TODO()
}

/**
 * Сортировка адресов
 *
 * Средняя
 *
 * Во входном файле с именем inputName содержатся фамилии и имена жителей города с указанием улицы и номера дома,
 * где они прописаны. Пример:
 *
 * Петров Иван - Железнодорожная 3
 * Сидоров Петр - Садовая 5
 * Иванов Алексей - Железнодорожная 7
 * Сидорова Мария - Садовая 5
 * Иванов Михаил - Железнодорожная 7
 *
 * Людей в городе может быть до миллиона.
 *
 * Вывести записи в выходной файл outputName,
 * упорядоченными по названию улицы (по алфавиту) и номеру дома (по возрастанию).
 * Людей, живущих в одном доме, выводить через запятую по алфавиту (вначале по фамилии, потом по имени). Пример:
 *
 * Железнодорожная 3 - Петров Иван
 * Железнодорожная 7 - Иванов Алексей, Иванов Михаил
 * Садовая 5 - Сидоров Петр, Сидорова Мария
 *
 * В случае обнаружения неверного формата файла бросить любое исключение.
 */
fun sortAddresses(inputName: String, outputName: String) {
    TODO()
}

/**
 * Сортировка температур
 *
 * Средняя
 * (Модифицированная задача с сайта acmp.ru)
 *
 * Во входном файле заданы температуры различных участков абстрактной планеты с точностью до десятых градуса.
 * Температуры могут изменяться в диапазоне от -273.0 до +500.0.
 * Например:
 *
 * 24.7
 * -12.6
 * 121.3
 * -98.4
 * 99.5
 * -12.6
 * 11.0
 *
 * Количество строк в файле может достигать ста миллионов.
 * Вывести строки в выходной файл, отсортировав их по возрастанию температуры.
 * Повторяющиеся строки сохранить. Например:
 *
 * -98.4
 * -12.6
 * -12.6
 * 11.0
 * 24.7
 * 99.5
 * 121.3
 * Трудоемкость - O(n)
 * Ресурсоемкость - O(1)
 */
fun sortTemperatures(inputName: String, outputName: String) {
    val list = IntArray(7731)

    File(inputName).bufferedReader().use {
        var line = it.readLine()
        while (line != null) {
            val index = (line.toDouble() * 10 + 2730).toInt()
            list[index]++
            line = it.readLine()
        }
    }

    File(outputName).bufferedWriter().use {
        for (i in list.indices) {
            while (list[i] > 0) {
                it.write(((i - 2730) / 10.0).toString())
                it.newLine()
                list[i]--
            }
        }
    }
/*    val map = mutableMapOf<Int, Int>()
    File(inputName).bufferedReader().use {
        var line = it.readLine()
        while (line != null) {
            val index = (line.toDouble() * 10 + 2730).toInt()
            map.putIfAbsent(index, 0)
            map[index] = map[index]!! + 1
            line = it.readLine()
        }
    }
    val res = map.toSortedMap(compareBy { it })
    File(outputName).bufferedWriter().use {
        for (entry in res) {
            var k = entry.value
            while (k != 0) {
                it.write((((entry.key - 2730) / 10.0).toString()))
                it.newLine()
                k--
            }
        }
    }*/
}

/**
 * Сортировка последовательности
 *
 * Средняя
 * (Задача взята с сайта acmp.ru)
 *
 * В файле задана последовательность из n целых положительных чисел, каждое в своей строке, например:
 *
 * 1
 * 2
 * 3
 * 2
 * 3
 * 1
 * 2
 *
 * Необходимо найти число, которое встречается в этой последовательности наибольшее количество раз,
 * а если таких чисел несколько, то найти минимальное из них,
 * и после этого переместить все такие числа в конец заданной последовательности.
 * Порядок расположения остальных чисел должен остаться без изменения.
 *
 * 1
 * 3
 * 3
 * 1
 * 2
 * 2
 * 2
 * Трудоемкость и ресурсоемкость - O(n)
 */
fun sortSequence(inputName: String, outputName: String) {
    val map = mutableMapOf<Int, Int>()
    var number = 0
    var occurrences = 0
    val text = mutableListOf<String>()

    File(inputName).bufferedReader().use {
        var line = it.readLine()
        while (line != null) {
            val lineValue = line.toInt()
            map[lineValue] = map.getOrPut(lineValue) { 0 } + 1
            if (occurrences == map[lineValue] && number > lineValue)
                number = lineValue
            if (occurrences < map[lineValue]!!) {
                occurrences = map[lineValue]!!
                number = lineValue
            }
            text.add(line)
            line = it.readLine()
        }
    }

    File(outputName).bufferedWriter().use {
        for (line in text) {
            if (line.toInt() != number) {
                it.write(line)
                it.newLine()
            }
        }
        for (i in 0 until occurrences) {
            it.write(number.toString())
            it.newLine()
        }
    }
}

/**
 * Соединить два отсортированных массива в один
 *
 * Простая
 *
 * Задан отсортированный массив first и второй массив second,
 * первые first.size ячеек которого содержат null, а остальные ячейки также отсортированы.
 * Соединить оба массива в массиве second так, чтобы он оказался отсортирован. Пример:
 *
 * first = [4 9 15 20 28]
 * second = [null null null null null 1 3 9 13 18 23]
 *
 * Результат: second = [1 3 4 9 9 13 15 20 23 28]
 */
fun <T : Comparable<T>> mergeArrays(first: Array<T>, second: Array<T?>) {
    TODO()
}

