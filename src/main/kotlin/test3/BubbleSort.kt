package test3

class BubbleSort<T>(private val inputValues: Iterable<T>) {
    fun sort(comparator: Comparator<T>): List<T> {
        val list = inputValues.toMutableList()
        if (list.size == 0) throw IndexOutOfBoundsException()

        for (pass in 0 until (list.size - 1)) {
            for (currentPosition in 0 until (list.size - pass - 1)) {
                if (comparator.compare(list[currentPosition], list[currentPosition + 1]) > 0) {
                    val tmp = list[currentPosition]
                    list[currentPosition] = list[currentPosition + 1]
                    list[currentPosition + 1] = tmp
                }
            }
        }

        return list
    }
}

fun getInputNumber(): Int {
    val input = readLine()
    return try {
        Integer.parseInt(input)
    } catch (e: NumberFormatException) {
        throw NumberFormatException()
    }
}

fun main() {
    println("Input amount of elements: ")
    val size = getInputNumber()
    val list = mutableListOf<Int>()
    val comparator: Comparator<Int>

    println("Input elements: ")
    for (i in 0 until size) {
        list.add(getInputNumber())
    }

    println("Input comparator (0 - natural order, 1 - reverse order): ")
    val comparatorType = getInputNumber()
    comparator = when (comparatorType) {
        0 -> Comparator.naturalOrder()
        1 -> Comparator.reverseOrder()
        else -> throw IllegalArgumentException()
    }

    println("Result: ")
    println(BubbleSort(list).sort(comparator))
}
