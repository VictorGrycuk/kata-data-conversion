package domain.serializers

import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.readValue
import domain.models.record.Record

class XmlSerializer: Serializer {
    override fun export(records: List<Record>): String {
        return XmlMapper().writeValueAsString(records)
    }

    override fun import(content: String): List<Record> {
        return XmlMapper().readValue(content)
    }
}