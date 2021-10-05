package infrastructure

import assertk.assertThat
import assertk.assertions.isEqualTo
import domain.exceptions.FolderNotFoundException
import domain.exceptions.HttpIOHandlerException
import infrastructure.io.FileIOHandler
import infrastructure.io.HttpIOHandler
import infrastructure.io.IOHandler
import org.junit.Test
import java.io.IOException
import java.nio.file.Paths

class IOHandlerTests {
    private val projectPath = Paths.get("").toAbsolutePath().toString()
    private val fileName = "file.txt"
    private lateinit var fileIO: IOHandler
    private lateinit var contentLocation: String
    private lateinit var content: String

    @Test(expected = FolderNotFoundException::class)
    fun `FileIO should check if a path exists when writing content`() {
        `given an IOHandler`(FileIOHandler())
        `given a path`("notFound")

        `when the handler writes content`()
    }

    @Test(expected = IOException::class)
    fun `FileIO should check if a path exists when reading content`() {
        `given an IOHandler`(FileIOHandler())
        `given a path`("notFound")

        `when the handler reads content`()
    }

    @Test
    fun `HttpIOHandler should be able to read online content`() {
        `given an IOHandler`(HttpIOHandler())
        `given an address`("https://pastebin.com/raw/Db8DKaCJ")

        `when the handler reads content`()

        `then the content should match`(getExpectedOnlineContent())
    }

    @Test(expected = HttpIOHandlerException::class)
    fun `HttpIOHandler should throw HttpIOHandlerException when unable to read online content`() {
        `given an IOHandler`(HttpIOHandler())
        `given an address`("https://pastebin.com/raw/!")

        `when the handler reads content`()
    }

    private fun `given an IOHandler`(ioHandler: IOHandler) {
        fileIO = ioHandler
    }

    private fun `given a path`(parentFolder: String) {
        contentLocation = Paths.get(projectPath, parentFolder, fileName).toString()
    }

    private fun `given an address`(address: String) {
        contentLocation = address
    }

    private fun `when the handler writes content`() = fileIO.writeContent(contentLocation, "test value")

    private fun `when the handler reads content`() {
        content = fileIO.readContent(contentLocation)
    }

    private fun `then the content should match`(expected: String) = assertThat(content).isEqualTo(expected)

    private fun getExpectedOnlineContent(): String {
        return "name,address,stars,contact,phone,url\r\n" +
                "The Abandoned,\"123 Fake Street\",5,Clancy Wiggum,+54 1-170-765-9933T1626,http://www.fake.com"
    }
}