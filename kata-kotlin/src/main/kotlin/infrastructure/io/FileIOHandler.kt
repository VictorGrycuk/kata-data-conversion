package infrastructure.io

import domain.exceptions.FolderNotFoundException
import java.io.File
import java.io.IOException
import java.lang.Exception
import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.exists


class FileIOHandler: IOHandler {
    override fun readContent(source: String): String {
        val sourceFile: Path
        try {
            sourceFile = Path(source)
        } catch (ex: Exception) {
            throw IOException("An error was found with the content file path: ${ex.message}")
        }

        if (!sourceFile.exists())
            throw IOException("The input file: $source was not found")

        val inputStream = File(source).inputStream()
        return inputStream.bufferedReader().use { it.readText() }
    }

    override fun writeContent(destination: String, value: String) {
        isValidFolder(destination)

        File(destination).printWriter().use { out -> out.println(value) }
    }

    private fun isValidFolder(filePath: String) {
        val parentFolder = Path(filePath).parent
        if (!parentFolder.exists())
            throw FolderNotFoundException("The folder $parentFolder was not found")
    }
}