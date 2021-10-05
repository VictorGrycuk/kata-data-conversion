package domain.rules

import domain.models.record.Record
import domain.models.ValidationResult
import java.lang.NumberFormatException

class ValidRatingRule(private val minValue: Int, private val maxValue: Int): Rule {
    override fun invoke(record: Record): ValidationResult {
        val rating: Int
        try {
            rating = record.stars.rawValue.toInt()
        } catch (ex: NumberFormatException) {
            return ValidationResult(false, "Invalid rating: ${record.stars.rawValue}")
        }

        return if (rating in minValue..maxValue)
            ValidationResult(true, "Success")
        else
            ValidationResult(false, "The value is out of the valid range ($minValue-$maxValue)")
    }
}