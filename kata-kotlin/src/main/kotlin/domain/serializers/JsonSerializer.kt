package domain.serializers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import domain.models.record.Record

class JsonSerializer: Serializer {
    override fun export(records: List<Record>): String {
        return jacksonObjectMapper().writeValueAsString(records)
    }

    override fun import(content: String): List<Record> {
        return jacksonObjectMapper().readValue(content)
    }
}