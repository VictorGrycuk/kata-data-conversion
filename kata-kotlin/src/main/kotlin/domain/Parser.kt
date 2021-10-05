package domain

import domain.models.record.Record
import infrastructure.io.FileIOHandler
import java.nio.file.Paths

class Parser(parserDI: ParserDI) {
    private val inputParser = parserDI.getInputParser()
    private val outputParser = parserDI.getOutputParser()
    private val inputIOHandler = parserDI.getInputSourceType()
    private val outputIOHandler = parserDI.getOutputType()
    private val inputSource = parserDI.getInputSource()
    private val outputDestination = parserDI.getOutputDestination()
    private val recordValidator = parserDI.getRecordValidator()
    private val printReport = parserDI.getShouldPrintReport()

    fun process(): String {
        var records = getRecords(inputSource)
        records = recordValidator.getValidRecords(records)

        exportValidRecords(records, outputDestination)

        if (printReport) exportInvalidRecords(recordValidator.getFullReport())

        return recordValidator.getShortReport()
    }

    private fun getRecords(source: String): List<Record> {
        val content = inputIOHandler.readContent(source)
        return inputParser.import(content)
    }

    private fun exportValidRecords(records: List<Record>, destination: String) {
        val serializedRecords = outputParser.export(records)
        outputIOHandler.writeContent(destination, serializedRecords)
    }

    private fun exportInvalidRecords(report: String) {
        val fileHandler = FileIOHandler()
        val filePath = Paths.get(Paths.get("").toAbsolutePath().toString(), "invalid-records.json").toString()
        fileHandler.writeContent(filePath, report)
    }
}