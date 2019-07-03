package ru.skillbranch.devintensive.utils

object Utils {
    fun parseFullName(fullName: String?): Pair<String?, String?> {
        var str = fullName?.trim()?.replaceAll("  ", " ");

        if (str.isNullOrBlank())
            return Pair(null, null)

        val parts = str.split(" ")

        val firstName = parts?.getOrNull(0)
        val lastName = parts?.getOrNull(1)

        return firstName to lastName
    }

    fun properForm(value: Int, oneForm: String, fewForm: String, manyForm: String ): String
    {
        var significantValue = value % 100
        if (significantValue in 10..20)
            return manyForm
        var lastDigit = value % 10
        if (lastDigit == 1)
            return oneForm
        if (lastDigit in 2 .. 4)
            return fewForm
        return manyForm
    }

    private fun String.replaceAll(oldValue: String, newValue: String): String {
        var result = this
        while (result.contains(oldValue)) {
            result = result.replace(oldValue, newValue)
        }
        return result
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        var res = ""
        if (firstName.isNullOrBlank() && lastName.isNullOrBlank())
            return null

        if (!firstName.isNullOrBlank())
            res += firstName[0].toUpperCase()

        if (!lastName.isNullOrBlank())
            res += lastName[0].toUpperCase()

        return res
    }

    fun transliteration(payload: String, divider: String = " "): String {
        val charsMap = mapOf(
            'а' to "a",
            'б' to "b",
            'в' to "v",
            'г' to "g",
            'д' to "d",
            'е' to "e",
            'ё' to "e",
            'ж' to "zh",
            'з' to "z",
            'и' to "i",
            'й' to "i",
            'к' to "k",
            'л' to "l",
            'м' to "m",
            'н' to "n",
            'о' to "o",
            'п' to "p",
            'р' to "r",
            'с' to "s",
            'т' to "t",
            'у' to "u",
            'ф' to "f",
            'х' to "h",
            'ц' to "c",
            'ч' to "ch",
            'ш' to "sh",
            'щ' to "sh'",
            'ъ' to "",
            'ы' to "i",
            'ь' to "",
            'э' to "e",
            'ю' to "yu",
            'я' to "ya"
        )
        return payload.map {
            when {
                it == ' ' -> divider
                it.isUpperCase() -> charsMap[it.toLowerCase()]?.capitalize() ?: it
                else -> charsMap[it] ?: it
            }
        }.joinToString ("")
    }
}