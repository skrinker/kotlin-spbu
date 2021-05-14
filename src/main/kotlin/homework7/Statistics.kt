package homework7

import kotlin.random.Random

class Statistics(private val sortMethod: SortMethod) {
    data class ChartValue(val size: Int, val averageTime: Long)
    fun getStatistics(
        maxArraySize: Int,
        stepSize: Int,
        stepAverage: Int
    ): MutableList<ChartValue> {
        val result = mutableListOf<ChartValue>()
        for (size in 0 until maxArraySize step stepSize)
            result.add(ChartValue(size, getAverageResult(size, stepAverage)))
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
        repeat(numberOfSteps) {
            val runningTime = calculateRunningTime(createRandomArray(arraySize))
            listOfResults.add(runningTime)
        }
        return listOfResults.average().toLong()
    }
}
