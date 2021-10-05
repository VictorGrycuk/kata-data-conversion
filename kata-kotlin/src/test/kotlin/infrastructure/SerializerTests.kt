package infrastructure

import assertk.assertThat
import assertk.assertions.isEqualTo
import domain.models.record.*
import infrastructure.io.FileIOHandler
import infrastructure.io.IOHandler
import domain.serializers.Serializer
import domain.serializers.CsvSerializer
import domain.serializers.JsonSerializer
import domain.serializers.XmlSerializer
import org.junit.Before
import org.junit.Test
import java.nio.file.Paths

class SerializerTests {

    private val totalRecordsNumber = 4000
    private val fileHandler: IOHandler = FileIOHandler()
    private lateinit var content: String
    private lateinit var record: Record
    private lateinit var records: List<Record>
    private lateinit var serializer: Serializer
    private lateinit var result: String
    private lateinit var expectedResult: String

    @Before
    fun setup() {
        record = Record(
            Hotel("The Gibson"),
            Address("63847 Lowe Knoll, East Maxine, WA 97030-4876"),
            Stars("5"),
            Contact("Dr. Sinda Wyman"),
            Phone("1-270-665-9933x1626"),
            URL("http://www.paucek.com/search.htm")
        )
    }

    @Test
    fun `the JsonSerializer should export a list of records as json string`() {
        `given a list of records`()
        `given a serializer`(JsonSerializer())

        `when the serializer processes the records`()

        `then the result should match the json expected result`()
    }

    @Test
    fun `the JsonSerializer should parse a file correctly`() {
        `given a serializer`(JsonSerializer())
        `given a file with records`("input.json")

        `when the serializer imports the file`()

        `then the list of records should match the file`()
        `then a record should contain all its properties`()
    }

    @Test
    fun `the CsvSerializer should parse a file correctly`() {
        `given a serializer`(CsvSerializer())
        `given a file with records`("input.csv")

        `when the serializer imports the file`()

        `then the list of records should match the file`()
        `then a record should contain all its properties`()
    }

    @Test
    fun `the CsvSerializer should export a list of records as json string`() {
        `given a list of records`()
        `given a serializer`(CsvSerializer())

        `when the serializer processes the records`()

        `then the result should match the csv expected result`()
    }

    @Test
    fun `the XmlSerializer should parse a file correctly`() {
        `given a serializer`(XmlSerializer())
        `given a file with records`("input.xml")

        `when the serializer imports the file`()

        `then the list of records should match the file`()
        `then a record should contain all its properties`()
    }

    @Test
    fun `the XmlSerializer should export a list of records as json string`() {
        `given a list of records`()
        `given a serializer`(XmlSerializer())

        `when the serializer processes the records`()

        `then the result should match the xml expected result`()
    }

    private fun `given a serializer`(serializer: Serializer) {
        this.serializer = serializer
    }

    private fun `given a file with records`(fileName: String) {
        val projectPath = Paths.get("").toAbsolutePath().toString()
        content = fileHandler.readContent(Paths.get(projectPath, "data", fileName).toString())
    }

    private fun `given a list of records`() {
        records = listOf(
            Record(
            Hotel("The Gibson"),
            Address("63847 Lowe Knoll, East Maxine, WA 97030-4876"),
            Stars("5"),
            Contact("Dr. Sinda Wyman"),
            Phone("1-270-665-9933x1626"),
            URL("http://www.paucek.com/search.htm")
        )
        )
    }

    private fun `when the serializer processes the records`() {
        result = serializer.export(records)
    }

    private fun `when the serializer imports the file`() {
        records = serializer.import(content)
    }

    private fun `then the result should match the json expected result`() {
        expectedResult = "[{\"hotel\":{\"rawValue\":\"The Gibson\"},\"address\":{\"rawValue\":\"63847 Lowe Knoll, East Maxine, WA 97030-4876\"},\"stars\":{\"rawValue\":\"5\"},\"contact\":{\"rawValue\":\"Dr. Sinda Wyman\"},\"phone\":{\"rawValue\":\"1-270-665-9933x1626\"},\"url\":{\"rawValue\":\"http://www.paucek.com/search.htm\"}}]"
        validateResults()
    }

    private fun `then the result should match the csv expected result`() {
        expectedResult = "address,contact,hotel,phone,stars,url\r\n" +
                "The Gibson,\"63847 Lowe Knoll, East Maxine, WA 97030-4876\",5,Dr. Sinda Wyman,1-270-665-9933x1626,http://www.paucek.com/search.htm\r\n"
        validateResults()
    }

    private fun `then the result should match the xml expected result`() {
        expectedResult = "<SingletonList><item><hotel><rawValue>The Gibson</rawValue></hotel><address><rawValue>63847 Lowe Knoll, East Maxine, WA 97030-4876</rawValue></address><stars><rawValue>5</rawValue></stars><contact><rawValue>Dr. Sinda Wyman</rawValue></contact><phone><rawValue>1-270-665-9933x1626</rawValue></phone><url><rawValue>http://www.paucek.com/search.htm</rawValue></url></item></SingletonList>"
        validateResults()
    }

    private fun `then the list of records should match the file`() =
        assertThat(records.count()).isEqualTo(totalRecordsNumber)

    private fun `then a record should contain all its properties`() =
        assertThat(records[0]).isEqualTo(record)

    private fun validateResults() = assertThat(result).isEqualTo(expectedResult)
}