package homework5

interface Node {
    val height: Int
    fun calculate(): Int
}

class OperatorNode(private val value: Int, override val height: Int) : Node {
    override fun toString(): String = "....".repeat(height) + value
    override fun calculate(): Int = value
}

class ParseOperandNode {
    private fun isCharSign(sign: String): Boolean = sign.matches(Regex("[+\\-*/]")) && (sign.length == 1)
    fun parse(inputList: MutableList<String>, height: Int): Node {
        val temp = inputList.removeFirst()
        if (isCharSign(temp)) {
            val leftNode = parse(inputList, height + 1)
            val rightNode = parse(inputList, height + 1)
            return OperandNode(temp, height, leftNode, rightNode)
        } else {
            return OperatorNode(temp.toInt(), height)
        }
    }
}

class OperandNode(
    private val sign: String,
    override val height: Int,
    private val leftNode: Node,
    private val rightNode: Node,
) : Node {
    override fun calculate(): Int {
        return when (sign) {
            "+" -> leftNode.calculate() + rightNode.calculate()
            "-" -> leftNode.calculate() - rightNode.calculate()
            "*" -> leftNode.calculate() * rightNode.calculate()
            "/" -> leftNode.calculate() / rightNode.calculate()
            else -> -1
        }
    }
    override fun toString(): String {
        return	"....".repeat(height) + sign + "\n" + leftNode.toString() + "\n" + rightNode.toString() + "\n"
    }
}
