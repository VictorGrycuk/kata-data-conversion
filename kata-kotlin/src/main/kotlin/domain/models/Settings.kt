package domain.models

data class Settings(
    val inputSource: String,
    val outputDestination: String,
    val inputFormat: FileFormat,
    val outputFormat: FileFormat,
    val IOType: IOType,
    val outputType: IOType,
    val printReport: Boolean
)