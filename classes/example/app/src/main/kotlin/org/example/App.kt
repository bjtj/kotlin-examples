package org.example

fun Any?.toString(): String {
    if (this == null) return "(this value is null)"
    return toString()
}

fun String?.toString(): String {
    if (this == null) return "(this string is null)"
    return toString()
}

fun main() {
    val number: Int? = 42
    val nothing: Any? = null
    val nullString: String? = null

    println(number.toString())
    println(nothing.toString())
    println(null.toString())
    println(nullString.toString())
}
