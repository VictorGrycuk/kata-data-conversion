package domain.rules

import domain.models.ValidationResult
import domain.models.record.Record

class HotelNameRule(private val validLength: Int = 100 ): Rule {
    override fun invoke(record: Record): ValidationResult {
        return if (record.hotel.rawValue.length <=validLength)
            ValidationResult(true, "Success")
        else
            ValidationResult(false, "Name is over $validLength characters long")
    }
}