package homework7

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class Matrix(private val matrix: Array<IntArray>) {
    private fun isMatrix(matrix: Array<IntArray>): Boolean =
        matrix.map { it.size }.distinct().size == 1 && matrix.isNotEmpty() && matrix[0].isNotEmpty()

    init {
        if (!isMatrix(matrix)) throw IllegalArgumentException("Incorrect matrix")
    }

    override fun toString(): String =
        matrix.joinToString(transform = { elements -> elements.joinToString { "$it" } }, separator = "\n")

    operator fun times(factor: Matrix): Matrix {
        val result = Array(matrix.size) { IntArray(factor.matrix.size) { 0 } }
        if (this.matrix[0].size == factor.matrix.size) {
            runBlocking {
                for (i in matrix.indices) {
                    for (j in factor.matrix.indices) {
                        for (k in factor.matrix.indices) {
                            launch {
                                result[i][j] += matrix[i][k] * factor.matrix[k][j]
                            }
                        }
                    }
                }
            }
        } else {
            throw IllegalArgumentException("Height and width are not equal")
        }
        return Matrix(result)
    }
}
