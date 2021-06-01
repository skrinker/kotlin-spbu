package test1

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

fun listsEqual(list1: List<Any>, list2: List<Any>): Boolean {
    if (list1.size != list2.size)
        return false
    val pairList = list1.zip(list2)
    return pairList.all { (elt1, elt2) ->
        elt1 == elt2
    }
}

internal class VectorTest {
    private val vector1 = Vector(listOf(IntVector(0), IntVector(1), IntVector(2)))
    private val vector2 = Vector(listOf(IntVector(3), IntVector(4), IntVector(5)))
    private val sum = vector1 + vector2
    private val diff = vector1 - vector2
    private val multiply = vector1 * vector2

    @Test
    fun <T : ArithmeticAvailable<T>> plus() {
        assertEquals(
            true,
            listsEqual(Vector(listOf(IntVector(3), IntVector(5), IntVector(7))).value, sum.value)
        )
    }

    @Test
    fun minus() {
        assertEquals(
            true,
            listsEqual(Vector(listOf(IntVector(-3), IntVector(-3), IntVector(-3))).value, diff.value)
        )
    }

    @Test
    fun times() {
        assertEquals(14, multiply.value)
    }
}
