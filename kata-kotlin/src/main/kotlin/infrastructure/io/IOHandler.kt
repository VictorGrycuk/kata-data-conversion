package infrastructure.io

interface IOHandler {
    fun readContent(source: String): String
    fun writeContent(destination: String, value: String)
}