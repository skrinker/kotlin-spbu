package homework6

import kotlin.random.Random

class Statistics(private val sortMethod: SortMethod) {
    fun getStatistics(
        maxArraySize: Int,
        offset: Int,
        numberOfSteps: Int
    ): MutableList<Pair<Int, Long>> {
        val result = mutableListOf<Pair<Int, Long>>()
        for (size in 0 until maxArraySize step offset)
            result.add(Pair(size, getAverageResult(size, numberOfSteps)))
        return result
    }

    private fun createRandomArray(size: Int): IntArray = IntArray(size) { Random.nextInt(0, Int.MAX_VALUE) }

    private fun calculateRunningTime(array: IntArray): Long {
        val startTime = System.currentTimeMillis()
        sortMethod.sort(array)
        val endTime = System.currentTimeMillis()
        return endTime - startTime
    }

    private fun getAverageResult(arraySize: Int, numberOfSteps: Int): Long {
        val listOfResults = mutableListOf<Long>()
        for (step in 1..numberOfSteps) {
            val runningTime = calculateRunningTime(createRandomArray(arraySize))
            listOfResults.add(runningTime)
        }
        return listOfResults.average().toLong()
    }
}
