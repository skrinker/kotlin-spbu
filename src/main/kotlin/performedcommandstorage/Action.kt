package performedcommandstorage
interface Action {
    fun execute()
    fun undo()
}

class InsertBack(private val value: Int, private val numbers: DequeOfNumbers) : Action {
    override fun execute() = numbers.insertBack(value)
    override fun undo() = numbers.deleteLast()
}

class Push(private val value: Int, private val numbers: DequeOfNumbers) : Action {
    override fun execute() = numbers.push(value)
    override fun undo() = numbers.pop()
}

class Rearrange(private val to: Int, private val from: Int, private val numbers: DequeOfNumbers) : Action {
    override fun execute() = numbers.rearrange(to, from)
    override fun undo() = numbers.rearrange(to, from)
}
