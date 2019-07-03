package ru.skillbranch.devintensive.extensions

fun String.truncate(len: Int = 16): String {
    var str = this.trimEnd()
    if (this?.isNullOrBlank()) return ""

    return if (str.length <= len) str else str.take(len + 1).trimEnd().plus("...")
}

fun String.stripHtml(): String {
    return this
        .replace("\\<.*?\\>".toRegex(RegexOption.MULTILINE), " ")
        .replace("\\s+".toRegex(RegexOption.MULTILINE), " ")
        .trim()
}


fun String.replaceAll(oldValue: String, newValue: String): String {
    var result = this
    while (result.contains(oldValue)) {
        result = result.replace(oldValue, newValue)
    }
    return result
}