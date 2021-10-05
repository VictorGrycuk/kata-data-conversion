package domain

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import domain.exceptions.ValidationNotRunException
import domain.models.InvalidRecord
import domain.models.record.Record
import domain.rules.Rule

class RecordValidator(private val rules: List<Rule>) {
    private lateinit var validRecords: List<Record>
    private lateinit var invalidRecords: List<InvalidRecord>

    fun getValidRecords(records: List<Record>): List<Record> {
        validRecords = emptyList()
        invalidRecords = emptyList()

        records.forEach { record ->
            val issues = getRecordIssues(record)
            if (issues.isEmpty()) validRecords = validRecords + record
            else invalidRecords = invalidRecords + InvalidRecord(record, issues)
        }

        return validRecords
    }

    fun getShortReport(): String {
        validateState()

        return "Valid records: ${ validRecords.count() }.${ System.lineSeparator() }Invalid records: ${ invalidRecords.count() }."
    }

    fun getFullReport(): String {
        validateState()
        return jacksonObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(invalidRecords)
    }

    private fun getRecordIssues(record: Record): List<String> {
        var reasons = emptyList<String>()

        rules
            .asSequence()
            .map { it(record) }
            .filterNot { it.isValid }
            .forEach { reasons = reasons + it.reason }

        return reasons
    }

    private fun validateState() {
        if (!this::validRecords.isInitialized || !this::invalidRecords.isInitialized)
            throw ValidationNotRunException("Validate the records before generating a report")
    }
}