package week1

import java.lang.Integer.max

fun generateParenthesisCombinations(num: Int): List<String> =
    generate(emptyList(), Generator("", 0, num, num))

data class Generator(val acc: String, val stillOpened: Int, val open: Int, val close: Int)

fun close(generator: Generator): Generator =
    generator.copy(
        stillOpened = max(0, generator.stillOpened - 1),
        close = max(0, generator.close - 1),
        acc = generator.acc + ")"
    )

fun open(generator: Generator): Generator =
    generator.copy(
        stillOpened = generator.stillOpened + 1,
        open = max(0, generator.open - 1),
        acc = generator.acc + "("
    )

private tailrec fun generate(accList: List<String>, generator: Generator): List<String> {
    return if (generator.open <= 0 && generator.close <= 0) {
        accList + listOf(generator.acc)
    } else {
        if (generator.stillOpened == 0 && generator.open > 0) {
            generate(
                accList,
                open(generator)
            )
        } else if (generator.stillOpened > 0 && generator.open > 0 && generator.close > 0) {
            generate(
                generate(accList, close(generator)),
                open(generator)
            )
        } else if (generator.stillOpened > 0 && generator.open <= 0 && generator.close > 0) {
            generate(accList, close(generator))
        } else {
            accList
        }
    }
}
