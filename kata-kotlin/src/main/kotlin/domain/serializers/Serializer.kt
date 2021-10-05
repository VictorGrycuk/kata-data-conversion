package domain.serializers

import domain.models.record.Record

interface Serializer {
    fun export(records: List<Record>): String
    fun import(content: String): List<Record>
}