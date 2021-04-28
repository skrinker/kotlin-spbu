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
        if (threads == 1) mergeSort(lowerBound, upperBound)
        val mid = (lowerBound + upperBound) / 2

        val lowerThread = Thread { this.mergeSortParallel(lowerBound, mid, threads / 2) }
        val upperThread = Thread { this.mergeSortParallel(mid, upperBound, threads - threads / 2) }
        lowerThread.run()
        upperThread.run()
        lowerThread.join()
        upperThread.join()

        this.mergeArraysParallel(lowerBound, upperBound, mid, threads - 2)
    }

    private fun IntArray.mergeSort(lowerBound: Int, upperBound: Int) {
        if (lowerBound + 1 >= upperBound) return
        val mid = (lowerBound + upperBound) / 2
        this.mergeSort(lowerBound, mid)
        this.mergeSort(mid, upperBound)

        this.mergeArrays(lowerBound, upperBound, mid)
    }

    private fun IntArray.binarySearch(target: Int): Int {
        var right = this.size
        var left = 0
        var mid = (left + right) / 2
        while (left < right) {
            if (this[mid] < target) left = mid + 1
            else right = mid
            mid = (left + right) / 2
        }
        return mid
    }

    private fun IntArray.mergeArraysParallel(left: Int, right: Int, mid: Int, threads: Int) {
        if (threads <= 1) {
            mergeArrays(left, right, mid)
            return
        }
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

    private fun IntArray.mergeArrays(left: Int, right: Int, mid: Int) {
        var first = 0
        var second = 0
        val result = IntArray(right - left) { 0 }

        while (left + first < mid && mid + second < right) {
            if (this[left + first] < this[mid + second]) {
                result[first + second] = this[left + second]
                ++first
            } else {
                result[first + second] = this[mid + second]
                ++first
            }
        }

        while (left + first < mid) {
            result[first + second] = this[left + first]
            ++first
        }

        while (mid + second < right) {
            result[first + second] = this[mid + second]
            ++second
        }

        for (i in 0 until right - left) {
            this[left + i] = result[i]
        }
    }

    override fun sort(array: IntArray, numberOfThreads: Int) {
        array.mergeSortParallel(0, array.size, 20)
    }
}

fun getMethodsList(): List<SortMethod> = mutableListOf<SortMethod>(DefaultSort, MergeSort)
