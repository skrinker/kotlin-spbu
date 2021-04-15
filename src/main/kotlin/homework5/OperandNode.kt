package homework5

interface Printable {
    fun print(height: Int)
}

interface Calculatetable {
    fun calculate(): Int
}

class ParseOperandNode {
    private fun isCharSign(sign: String): Boolean = sign.matches(Regex("[+\\-*/]")) && (sign.length == 1)
    fun parse(inputList: MutableList<String>): OperandNode {
        val temp = inputList.removeFirst()
        if (isCharSign(temp)) {
            val leftNode = parse(inputList)
            val rightNode = parse(inputList)
            return OperandNode(temp, -1, leftNode, rightNode)
        } else {
            return OperandNode(null, temp.toInt(), null, null)
        }
    }
}

class OperandNode(
    private val sign: String?,
    private var value: Int,
    private val leftNode: OperandNode?,
    private val rightNode: OperandNode?,
) : Printable, Calculatetable {
    override fun calculate(): Int {
        return if (leftNode != null && rightNode != null) {
            when (sign) {
                "+" -> leftNode.calculate() + rightNode.calculate()
                "-" -> leftNode.calculate() - rightNode.calculate()
                "*" -> leftNode.calculate() * rightNode.calculate()
                "/" -> leftNode.calculate() / rightNode.calculate()
                else -> -1
            }
        } else {
            value
        }
    }

    override fun print(height: Int) {
        if (sign != null && leftNode != null && rightNode != null) {
            println("....".repeat(height) + sign)
            leftNode.print(height + 1)
            rightNode.print(height + 1)
        } else {
            println("....".repeat(height) + value)
        }
    }
}
