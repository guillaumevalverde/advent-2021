package week1

sealed class JsonElement
data class JsonObject(val fields: Map<String, JsonElement>) : JsonElement() {
    constructor(vararg fields: Pair<String, JsonElement>) : this(fields.toMap())
}

data class JsonArray(val elements: List<JsonElement>) : JsonElement() {
    constructor(vararg elements: JsonElement) : this(elements.toList())
}

data class JsonNumber(val value: Double) : JsonElement()
data class JsonString(val value: String) : JsonElement()
data class JsonBoolean(val value: Boolean) : JsonElement()
object JsonNull : JsonElement()

private fun String.addSeparator(): String = if (this.isEmpty()) "" else "$this,"

private fun JsonArray.stringifyArray() = this.elements.fold("") { acc, ell -> "${acc.addSeparator()}${ell.stringify()}" }

private fun List<Pair<String, JsonElement>>.stringifyList() =
    this.fold("") { acc, ell -> "${acc.addSeparator()}\"${ell.first}\":${ell.second.stringify()}"}

private fun Double.stringifyDouble() : String =
    if ((this.toInt() - this) == 0.0  ) this.toInt().toString() else this.toString()

fun JsonElement.stringify(): String =
    when(this) {
        is JsonArray -> "[${this.stringifyArray()}]"
        is JsonBoolean -> if (this.value) "true" else "false"
        JsonNull -> "null"
        is JsonNumber -> this.value.stringifyDouble()
        is JsonObject -> "{${this.fields.toList().stringifyList()}}"
        is JsonString -> "\"${this.value}\""
    }
