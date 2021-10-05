package domain

import assertk.assertThat
import assertk.assertions.isEqualTo
import domain.exceptions.HttpIOHandlerException
import domain.exceptions.ValidationNotRunException
import domain.models.record.*
import domain.rules.HotelNameRule
import domain.rules.Rule
import domain.rules.ValidRatingRule
import org.junit.Test

class RecordValidatorTests {

    private lateinit var validator: RecordValidator
    private lateinit var rules: List<Rule>
    private lateinit var records: List<Record>
    private lateinit var validRecords: List<Record>
    private lateinit var report: String

    @Test
    fun `RecordValidator should return a list of valid records`() {
        `given a set of rules`()
        `given a set of records`()
        `given a RecordValidator`()

        `when getValidRecords is called`()

        `then it should return a list of valid records`()
    }

    @Test
    fun `RecordValidator should return a short report`() {
        `given a set of rules`()
        `given a set of records`()
        `given a RecordValidator`()
        `given validated records`()

        `when getShortReport is called`()

        `then the report should match`("Valid records: 1.${System.lineSeparator()}Invalid records: 1.")
    }

    @Test(expected = ValidationNotRunException::class)
    fun `RecordValidator should throw exception when asked for a report before validation`() {
        `given a set of rules`()
        `given a set of records`()
        `given a RecordValidator`()

        `when getFullReport is called`()
    }

    @Test
    fun `RecordValidator should return report of invalid records`() {
        `given a set of rules`()
        `given a set of records`()
        `given a RecordValidator`()
        `given validated records`()

        `when getFullReport is called`()

        `then the full report should contain the invalid records`()
    }

    private fun `given a set of rules`() {
        rules = listOf(HotelNameRule(20), ValidRatingRule(0, 5))
    }

    private fun `given a set of records`() {
        val record = Record(
            Hotel("The Gibson"),
            Address("63847 Lowe Knoll, East Maxine, WA 97030-4876"),
            Stars("5"),
            Contact("Dr. Sinda Wyman"),
            Phone("1-270-665-9933x1626"),
            URL("http://www.paucek.com/search.htm")
        )

        records = listOf(
            record,
            record.copy(
                hotel = Hotel("An invalid hotel name because its too long"),
                stars = Stars("-1")
            )
        )
    }

    private fun `given a RecordValidator`() {
        validator = RecordValidator(rules)
    }

    private fun `given validated records`() = validateRecords()

    private fun `when getValidRecords is called`() = validateRecords()

    private fun `when getFullReport is called`() {
        report = validator.getFullReport()
    }

    private fun `when getShortReport is called`() {
        report = validator.getShortReport()
    }

    private fun `then it should return a list of valid records`() {
        assertThat(validRecords.count()).isEqualTo(1)
        assertThat(validRecords.contains(records.first()))
    }

    private fun `then the full report should contain the invalid records`() =
        assertThat(report.replace("\r\n", "\n")).isEqualTo(getExpectedReport())

    private fun `then the report should match`(expected: String) {
        assertThat(report).isEqualTo(expected)
    }

    private fun validateRecords() {
        validRecords = validator.getValidRecords(records)
    }

    private fun getExpectedReport(): String {
        return """[ {
  "record" : {
    "hotel" : {
      "rawValue" : "An invalid hotel name because its too long"
    },
    "address" : {
      "rawValue" : "63847 Lowe Knoll, East Maxine, WA 97030-4876"
    },
    "stars" : {
      "rawValue" : "-1"
    },
    "contact" : {
      "rawValue" : "Dr. Sinda Wyman"
    },
    "phone" : {
      "rawValue" : "1-270-665-9933x1626"
    },
    "url" : {
      "rawValue" : "http://www.paucek.com/search.htm"
    }
  },
  "issues" : [ "Name is over 20 characters long", "The value is out of the valid range (0-5)" ]
} ]""".replace("\r\n", "\n")
    }
}
