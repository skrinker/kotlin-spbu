package test1

interface ArithmeticAvailable<T : ArithmeticAvailable<T>> {
    operator fun plus(other: T): T
    operator fun minus(other: T): T
    operator fun times(other: T): T
}

class IntVector(val value: Int) : ArithmeticAvailable<IntVector> {
    override fun minus(other: IntVector) = IntVector(value - other.value)
    override fun plus(other: IntVector) = IntVector(value + other.value)
    override fun times(other: IntVector) = IntVector(value * other.value)
}

class Vector<T : ArithmeticAvailable<T>>(val value: List<T>) {
    private val size = value.size

    operator fun plus(other: Vector<T>): Vector<T> {
        require(this.size == other.size) {
            throw IllegalArgumentException()
        }
        return Vector(value.zip(other.value) { a, b -> a + b })
    }

    operator fun minus(other: Vector<T>): Vector<T> {
        require(this.size == other.size) {
            throw IllegalArgumentException()
        }
        return Vector(value.zip(other.value) { a, b -> a - b })
    }

    operator fun times(other: Vector<T>): T {
        require(this.size == other.size) {
            throw IllegalArgumentException()
        }
        val list = mutableListOf<T>()
        for (i in 0 until this.size) {
            list.add(this.value[i] * other.value[i])
        }
        return list.reduce { a, b -> a + b }
    }
}
