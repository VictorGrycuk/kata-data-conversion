package domain.rules

import domain.models.record.Record
import domain.models.ValidationResult

interface Rule {
    operator fun invoke(record: Record): ValidationResult
}