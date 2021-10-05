package domain.rules

import domain.models.record.Record
import domain.models.ValidationResult
import org.apache.commons.validator.routines.UrlValidator

class ValidUrlRule: Rule {
    override fun invoke(record: Record): ValidationResult {
        return if (UrlValidator().isValid(record.url.rawValue))
            ValidationResult(true, "Success")
        else
            ValidationResult(false, "The given URL is not valid")
    }
}