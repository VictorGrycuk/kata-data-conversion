package domain.models.record

data class Record(
    val hotel: Hotel = Hotel(),
    val address: Address = Address(),
    val stars: Stars = Stars(),
    val contact: Contact = Contact(),
    val phone: Phone = Phone(),
    val url: URL = URL()
)