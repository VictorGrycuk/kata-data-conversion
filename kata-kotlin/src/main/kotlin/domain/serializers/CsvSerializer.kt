package domain.serializers

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import domain.models.record.*
import java.io.ByteArrayOutputStream
import kotlin.reflect.full.memberProperties

class CsvSerializer: Serializer {
    override fun export(records: List<Record>): String {
        val stream = ByteArrayOutputStream()
        val header = getHeader()

        csvWriter().writeAll(listOf(header) + getRecords(records), stream)

        return stream.toString()
    }

    override fun import(content: String): List<Record> {
        return csvReader().readAllWithHeader(content)
            .map { x -> Record(
                Hotel(x["name"] ?: ""),
                Address(x["address"] ?: ""),
                Stars(x["stars"] ?: ""),
                Contact(x["contact"] ?: ""),
                Phone(x["phone"] ?: ""),
                URL(x["url"] ?: "")
            ) }
    }

    private fun getHeader() = Record::class.memberProperties.map { it.name }

    private fun getRecords(records: List<Record>): List<List<String>> {
        return records.map {
            listOf(
                it.hotel.rawValue,
                it.address.rawValue,
                it.stars.rawValue,
                it.contact.rawValue,
                it.phone.rawValue,
                it.url.rawValue
            )
        }
    }
}