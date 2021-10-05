import domain.CliAdapter
import domain.Parser
import domain.ParserDI
import java.lang.Exception

fun main(args: Array<String>) {
    try {
        val settings = CliAdapter(args)
        val parser = Parser(ParserDI(settings))

        println(parser.process())
    } catch (ex: Exception) {
        println(ex.message ?: "An unknown error has occurred.")
    }
}