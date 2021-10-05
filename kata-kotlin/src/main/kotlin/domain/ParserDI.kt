package domain

import domain.models.FileFormat
import domain.models.Settings
import domain.models.IOType
import domain.rules.HotelNameRule
import domain.rules.Rule
import domain.rules.ValidRatingRule
import domain.rules.ValidUrlRule
import domain.serializers.CsvSerializer
import domain.serializers.JsonSerializer
import domain.serializers.Serializer
import domain.serializers.XmlSerializer
import infrastructure.io.CliIOHandler
import infrastructure.io.FileIOHandler
import infrastructure.io.HttpIOHandler
import infrastructure.io.IOHandler

class ParserDI(private val settings: Settings) {
    fun getInputParser(): Serializer {
        return when(settings.inputFormat) {
            FileFormat.CSV -> CsvSerializer()
            FileFormat.JSON -> JsonSerializer()
            FileFormat.XML -> XmlSerializer()
        }
    }

    fun getOutputParser(): Serializer {
        return when(settings.outputFormat) {
            FileFormat.CSV -> CsvSerializer()
            FileFormat.JSON -> JsonSerializer()
            FileFormat.XML -> XmlSerializer()
        }
    }

    fun getInputSourceType(): IOHandler {
        return when(settings.IOType) {
            IOType.FILE -> FileIOHandler()
            IOType.CLI -> CliIOHandler()
            IOType.HTTP -> HttpIOHandler()
        }
    }

    fun getOutputType(): IOHandler {
        return when(settings.outputType) {
            IOType.FILE -> FileIOHandler()
            IOType.CLI -> CliIOHandler()
            IOType.HTTP -> HttpIOHandler()
        }
    }

    private fun getRules(): List<Rule> {
        return listOf(
            HotelNameRule(),
            ValidRatingRule(0, 5),
            ValidUrlRule()
        )
    }

    fun getRecordValidator() = RecordValidator(getRules())

    fun getInputSource() = settings.inputSource

    fun getOutputDestination() = settings.outputDestination

    fun getShouldPrintReport() = settings.printReport
}