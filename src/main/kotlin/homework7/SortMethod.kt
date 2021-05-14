package homework7

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.math.pow

interface SortMethod {
    val name: String
    fun sort(array: IntArray)
}

class DefaultSort : SortMethod {
    override val name: String = "Default Kotlin sort"
    override fun sort(array: IntArray) = array.sort()
}

class MergeSort(private val numberOfThreads: Int) : SortMethod {
    override val name: String = "Merge sort($numberOfThreads threads)"

    data class ArraySegment(val lowerBound: Int, val upperBound: Int) {
        val size: Int = upperBound - lowerBound + 1
    }

    private suspend fun IntArray.mergeSortParallel(
        arraySegment: ArraySegment,
        result: IntArray,
        pasteBound: Int,
        threads: Int
    ) {
        if (arraySegment.size == 1) {
            result[pasteBound] = this[arraySegment.lowerBound]
        } else {
            val tempArray = IntArray(arraySegment.size) { 0 }
            val mid = (arraySegment.lowerBound + arraySegment.upperBound) / 2
            val newMid = mid - arraySegment.lowerBound
            if (threads <= 1) {
                this.mergeSortParallel(ArraySegment(arraySegment.lowerBound, mid), tempArray, 0, 1)
                this.mergeSortParallel(ArraySegment(mid + 1, arraySegment.upperBound), tempArray, newMid + 1, 1)
            } else {
                val thisArray = this
                coroutineScope {
                    val lowerThread = launch {
                        thisArray.mergeSortParallel(
                            ArraySegment(arraySegment.lowerBound, mid),
                            tempArray,
                            0,
                            threads / 2
                        )
                    }
                    val upperThread = launch {
                        thisArray.mergeSortParallel(
                            ArraySegment(mid + 1, arraySegment.upperBound),
                            tempArray,
                            newMid + 1,
                            threads - threads / 2
                        )
                    }
                    lowerThread.join()
                    upperThread.join()
                }
            }
            tempArray.mergeArraysParallel(
                ArraySegment(0, newMid),
                ArraySegment(newMid + 1, arraySegment.size - 1),
                result,
                pasteBound,
                threads
            )
        }
    }

    private fun IntArray.binarySearch(target: Int, lowerBound: Int, upperBound: Int): Int {
        var left = lowerBound
        var right = upperBound + 1
        while (left < right) {
            val mid = (left + right) / 2
            if (target <= this[mid]) {
                right = mid
            } else {
                left = mid + 1
            }
        }
        return right
    }

    private suspend fun IntArray.mergeArraysParallel(
        firstSegment: ArraySegment,
        secondSegment: ArraySegment,
        mergeArray: IntArray,
        pasteBound: Int,
        threads: Int
    ) {
        if (firstSegment.size < secondSegment.size) {
            this.mergeArraysParallel(secondSegment, firstSegment, mergeArray, pasteBound, threads)
            return
        }
        if (firstSegment.size == 0) return
        val firstMid = (firstSegment.upperBound + firstSegment.lowerBound) / 2
        val secondMid = this.binarySearch(this[firstMid], secondSegment.lowerBound, secondSegment.upperBound)
        val pasteMidBound = pasteBound + firstMid - firstSegment.lowerBound + secondMid - secondSegment.lowerBound
        mergeArray[pasteMidBound] = this[firstMid]
        val thisArray = this
        coroutineScope {
            val lowerThread = launch {
                thisArray.mergeArraysParallel(
                    ArraySegment(firstSegment.lowerBound, firstMid - 1),
                    ArraySegment(secondSegment.lowerBound, secondMid - 1),
                    mergeArray,
                    pasteBound,
                    threads / 2
                )
            }
            val upperThread = launch {
                thisArray.mergeArraysParallel(
                    ArraySegment(firstMid + 1, firstSegment.upperBound),
                    ArraySegment(secondMid, secondSegment.upperBound),
                    mergeArray,
                    pasteMidBound + 1,
                    threads - threads / 2
                )
            }
            lowerThread.join()
            upperThread.join()
        }
    }

    override fun sort(array: IntArray) {
        if (array.isEmpty()) return
        val tempArray = IntArray(array.size) { 0 }
        runBlocking {
            array.mergeSortParallel(ArraySegment(0, array.size - 1), tempArray, 0, numberOfThreads)
        }
        tempArray.copyInto(array)
    }
}

fun getMethodsList(lowerBound: Int, upperBound: Int): List<SortMethod> {
    val methodsList = mutableListOf<SortMethod>(DefaultSort())
    for (degreeSize in lowerBound..upperBound) {
        methodsList.add(MergeSort(2.0.pow(degreeSize).toInt()))
    }
    return methodsList
}
