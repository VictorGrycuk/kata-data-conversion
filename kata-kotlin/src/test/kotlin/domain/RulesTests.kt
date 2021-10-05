package domain

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import domain.models.ValidationResult
import domain.models.record.*
import org.junit.Test
import domain.rules.HotelNameRule
import domain.rules.Rule
import domain.rules.ValidRatingRule
import domain.rules.ValidUrlRule
import org.junit.Before

class RulesTests {
    private val validLength = 100
    private lateinit var rule: Rule
    private lateinit var value: Any
    private lateinit var record: Record
    private lateinit var result: ValidationResult

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
    fun `HotelNameRule should return true when hotel name is equal or below 100`() {
        `given a HotelNameRule`()
        `given a name of length 100`()

        `when the rule is applied`()

        `then the result should return valid`()
        `then the result text must be`("Success")
    }

    @Test
    fun `HotelNameRule should return false when hotel name is above 100`() {
        `given a HotelNameRule`()
        `given a name of length 101`()

        `when the rule is applied`()

        `then the result should return invalid`()
        `then the result text must be`("Name is over $validLength characters long")
    }

    @Test
    fun `ValidRatingRule should return true when the rating is within the valid range`() {
        `given a ValidRatingRule`()
        `given a valid rating`()

        `when the rule is applied`()

        `then the result should return valid`()
        `then the result text must be`("Success")
    }

    @Test
    fun `ValidRatingRule should return false when the rating is not within the valid range`() {
        `given a ValidRatingRule`()
        `given an invalid rating`()

        `when the rule is applied`()

        `then the result should return invalid`()
        `then the result text must be`("The value is out of the valid range (0-5)")
    }

    @Test
    fun `ValidUrlRule should return true when the given URL is valid`() {
        `given a ValidUrlRule`()
        `given a valid URL`()

        `when the rule is applied`()

        `then the result should return valid`()
        `then the result text must be`("Success")
    }

    @Test
    fun `ValidUrlRule should return false when the given URL is invalid`() {
        `given a ValidUrlRule`()
        `given an invalid URL`()

        `when the rule is applied`()

        `then the result should return invalid`()
        `then the result text must be`("The given URL is not valid")
    }

    private fun `given a HotelNameRule`() {
        rule = HotelNameRule(validLength)
    }

    private fun `given a ValidRatingRule`() {
        rule = ValidRatingRule(0, 5)
    }

    private fun `given a ValidUrlRule`() {
        rule = ValidUrlRule()
    }

    private fun `given a name of length 100`() {
        val name = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean m"
        record = record.copy(hotel = Hotel(name))
    }

    private fun `given a name of length 101`() {
        val longName = "Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, tot"
        record = record.copy(hotel = Hotel(longName))
    }

    private fun `given a valid rating`() {
        value = 5
    }

    private fun `given an invalid rating`() {
        record = record.copy(stars = Stars("6"))
    }

    private fun `given a valid URL`() {
        value = "https://www.test.com/"
    }

    private fun `given an invalid URL`() {
        record = record.copy(url = URL("this is not a valid url"))
    }

    private fun `when the rule is applied`() {
        result = rule(record)
    }

    private fun `then the result should return valid`() = assertThat(result.isValid).isTrue()

    private fun `then the result should return invalid`() = assertThat(result.isValid).isFalse()

    private fun `then the result text must be`(value: String) = assertThat(result.reason).isEqualTo(value)
}
