package homework6

import kotlin.random.Random

const val THOUSAND = 1000

class Statistics(private val sortMethod: SortMethod) {
    fun getStatistics(
        maxArraySize: Int,
        offset: Int,
        numberOfSteps: Int,
        numberOfThreads: Int
    ): MutableList<Pair<Int, Long>> {
        val result = mutableListOf<Pair<Int, Long>>()
        for (size in 0 until maxArraySize step offset)
            result.add(Pair(size, getAverageResult(size, numberOfSteps, numberOfThreads)))
        return result
    }

    private fun createRandomArray(size: Int): IntArray = IntArray(size) { Random.nextInt(0, THOUSAND) }

    private fun calculateRunningTime(array: IntArray, numberOfThreads: Int): Long {
        val startTime = System.nanoTime()
        sortMethod.sort(array, numberOfThreads)
        val endTime = System.nanoTime()
        return endTime - startTime
    }

    private fun getAverageResult(arraySize: Int, numberOfSteps: Int, numberOfThreads: Int): Long {
        val listOfResults = mutableListOf<Long>()
        for (step in 1..numberOfSteps) {
            val runningTime = calculateRunningTime(createRandomArray(arraySize), numberOfThreads)
            listOfResults.add(runningTime)
        }
        return listOfResults.average().toLong()
    }
}
