package infrastructure.io

import domain.exceptions.InvalidInputException
import java.util.*
import kotlin.collections.ArrayList

class CliIOHandler: IOHandler {
    override fun readContent(source: String): String {
        val input = Scanner(System.`in`)
        val lines: MutableList<String> = ArrayList()
        var lineNew: String

        while (input.hasNextLine()) {
            lineNew = input.nextLine()
            println(lineNew)
            lines.add(lineNew)
        }

        if (lines.count() == 0)
            throw InvalidInputException("Invalid or missing input.")

        return lines.joinToString(System.lineSeparator())
    }

    override fun writeContent(destination: String, value: String) {
        println(value)
    }
}