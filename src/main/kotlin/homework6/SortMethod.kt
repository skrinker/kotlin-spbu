package homework6

interface SortMethod {
    val name: String
    fun sort(array: IntArray, numberOfThreads: Int)
}

object DefaultSort : SortMethod {
    override val name: String = "Default Kotlin sort"
    override fun sort(array: IntArray, numberOfThreads: Int) = array.sort()
}

object MergeSort : SortMethod {
    override val name: String = "Merge sort"

    private fun IntArray.mergeSortParallel(lowerBound: Int, upperBound: Int, threads: Int) {
        if (lowerBound + 1 >= upperBound) return
        val mid = (lowerBound + upperBound) / 2

        val lowerThread = Thread { this.mergeSortParallel(lowerBound, mid, threads / 2) }
        val upperThread = Thread { this.mergeSortParallel(mid, upperBound, threads - threads / 2) }
        lowerThread.run()
        upperThread.run()
        lowerThread.join()
        upperThread.join()

        this.mergeArraysParallel(lowerBound, upperBound, mid)
    }

    private fun IntArray.binarySearch(target: Int): Int {
        var right = this.size
        var left = 0
        while (left < right) {
            val mid = (left + right) / 2
            if (this[mid] < target) left = mid + 1
            else right = mid
        }
        return right
    }

    private fun IntArray.mergeArraysParallel(left: Int, right: Int, mid: Int) {
        val rightArray = this.copyOfRange(mid, right)
        val leftArray = this.copyOfRange(left, mid)
        val result = IntArray(right - left) { 0 }
        val lowerThread = Thread {
            for (i in 0 until mid - left)
                result[i + rightArray.binarySearch(leftArray[i])] = leftArray[i]
        }
        val upperThread = Thread {
            for (i in 0 until right - mid)
                result[i + leftArray.binarySearch(rightArray[i])] = rightArray[i]
        }
        lowerThread.run()
        upperThread.run()
        lowerThread.join()
        upperThread.join()

        for (i in 0 until right - left)
            this[left + i] = result[i]
    }

    override fun sort(array: IntArray, numberOfThreads: Int) =
        array.mergeSortParallel(0, array.size, numberOfThreads)
}

fun getMethodsList(): List<SortMethod> = mutableListOf<SortMethod>(DefaultSort, MergeSort)
