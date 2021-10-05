package domain

import domain.models.FileFormat
import domain.models.IOType
import domain.models.Settings
import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.default

object CliAdapter {
    operator fun invoke(args: Array<String>): Settings {
        val argsParser = ArgParser("parser")

        val inputSource by argsParser.option(
            ArgType.String,
            shortName = "i",
            fullName = "inputSource",
            description = "Source location of the input content (absolute file path for files)."
        )

        val inputType by argsParser.option(
            ArgType.Choice<IOType>(),
            shortName = "t",
            fullName = "inputType",
            description = "The type of source for the input content"
        ).default(IOType.CLI)

        val inputSerializationFormat by argsParser.option(
            ArgType.Choice<FileFormat>(),
            shortName = "f",
            fullName = "inputSerialization",
            description = "Serialization format of the input content"
        ).default(FileFormat.CSV)

        val outputDestination by argsParser.option(
            ArgType.String,
            shortName = "o",
            fullName = "outputDestination",
            description = "Destination for the output content (absolute file path for files)"
        )

        val outputType by argsParser.option(
            ArgType.Choice<IOType>(),
            shortName = "d",
            fullName = "outputType",
            description = "The type of destination for the output content"
        ).default(IOType.CLI)

        val outputSerializationFormat by argsParser.option(
            ArgType.Choice<FileFormat>(),
            shortName = "s",
            fullName = "outputSerialization",
            description = "Serialization format for the output content"
        ).default(FileFormat.JSON)

        val printReport by argsParser.option(
            ArgType.Boolean,
            shortName = "p",
            fullName = "printReport",
            description = "if true, it will export a JSON file of the invalid records and their issues"
        ).default(false)

        argsParser.parse(args)

        return Settings(
            inputSource ?: "",
            outputDestination ?: "",
            inputSerializationFormat,
            outputSerializationFormat,
            inputType,
            outputType,
            printReport
        )
    }
}