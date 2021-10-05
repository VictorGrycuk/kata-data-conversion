package domain

import assertk.assertThat
import assertk.assertions.isEqualTo
import domain.models.FileFormat
import domain.models.IOType
import domain.models.Settings
import org.junit.Test
import java.nio.file.Paths

class ParserTests {
    private lateinit var parser: Parser
    private lateinit var settings: Settings
    private lateinit var result: String

    private val projectPath = Paths.get("").toAbsolutePath().toString()

    @Test
    fun `when the parser processes the sample csv data it should return the correct result`() {
        `given a settings with a csv data source`()
        `given a parser`()

        `when the parses processes the input`()

        `then the result should match`("Valid records: 3996.${System.lineSeparator()}Invalid records: 4.")
    }

    @Test
    fun `when the parser processes the sample json data it should return the correct result`() {
        `given a settings with a json data source`()
        `given a parser`()

        `when the parses processes the input`()

        `then the result should match`("Valid records: 3996.${System.lineSeparator()}Invalid records: 4.")
    }

    @Test
    fun `when the parser processes the sample xml data it should return the correct result`() {
        `given a settings with a xml data source`()
        `given a parser`()

        `when the parses processes the input`()

        `then the result should match`("Valid records: 3996.${System.lineSeparator()}Invalid records: 4.")
    }

    private fun `given a settings with a csv data source`() {
        settings = getGenericSettings().copy(inputSource = getCsvSamplePath())
    }

    private fun `given a settings with a json data source`() {
        settings = getGenericSettings().copy(
            inputSource = getJsonSamplePath(),
            inputFormat = FileFormat.JSON
        )
    }

    private fun `given a settings with a xml data source`() {
        settings = getGenericSettings().copy(
            inputSource = getXmlSamplePath(),
            inputFormat = FileFormat.XML
        )
    }

    private fun `given a parser`() {
        parser = Parser(ParserDI(settings))
    }

    private fun `when the parses processes the input`() {
        result = parser.process()
    }

    private fun `then the result should match`(result: String) = assertThat(this.result).isEqualTo(result)

    private fun getGenericSettings(): Settings {
        return Settings(
            getCsvSamplePath(),
            "dummy",
            FileFormat.CSV,
            FileFormat.JSON,
            IOType.FILE,
            IOType.CLI,
            false
        )
    }

    private fun getCsvSamplePath(): String = Paths.get(projectPath, "data", "input.csv").toString()

    private fun getJsonSamplePath(): String = Paths.get(projectPath, "data", "input.json").toString()

    private fun getXmlSamplePath(): String = Paths.get(projectPath, "data", "input.xml").toString()
}