package domain.models

import domain.models.record.Record

class InvalidRecord(val record: Record, val issues: List<String>)