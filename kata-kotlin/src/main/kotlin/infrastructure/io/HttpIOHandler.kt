package infrastructure.io

import domain.exceptions.HttpIOHandlerException
import java.net.HttpURLConnection
import java.net.URL

class HttpIOHandler: IOHandler {
    override fun readContent(source: String): String {
        val content: String
        val url = URL(source)
        val urlConnection = url.openConnection() as HttpURLConnection

        try {
            content = urlConnection.inputStream.bufferedReader().readText()
        } catch (ex: Exception) {
            val message = "An issue occurred while trying to read the online source: ${ex.message ?: "Unknown error" }"
            throw HttpIOHandlerException(message)
        } finally {
            urlConnection.disconnect()
        }

        if (content.isBlank()) throw HttpIOHandlerException("The online source is blank")

        return content
    }

    override fun writeContent(destination: String, value: String) {
        // This method can contain logic to upload the result
        // content through an API or some other connection.

        // But for the sake of simplicity, lets simulate that its uploading something
        Thread.sleep(5_000)
    }
}