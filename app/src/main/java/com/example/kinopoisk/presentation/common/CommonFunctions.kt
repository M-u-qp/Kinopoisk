package com.example.kinopoisk.presentation.common

import java.util.Calendar

//Перенос слов на вторую строку, если не убирается название фильма
 fun normalizeTitleMovie(name: String): String {
    val words = name.split(" ")
    val lines = mutableListOf<String>()
    var currentLine = ""
    for ((index, word) in words.withIndex()) {
        if (index == 0 && word.length >= 13) {
            currentLine = word.substring(0, 13)
            lines.add(currentLine)
            currentLine = ""
        } else if (currentLine.length + word.length + 1 <= 13) {
            currentLine += " $word"
        } else {
            lines.add(currentLine)
            currentLine = if (word.length >= 10) {
                word.substring(0, 10) + "..."
            } else {
                word
            }
        }
    }
    if (currentLine.isNotEmpty()) {
        lines.add(currentLine)
    }
    return lines.joinToString("\n ")
}

//Возвращает диапазон в виде списка Int от текущего года и 100 лет назад
fun listLast100Years(): List<Int> {
    val currentYear = Calendar.getInstance().get(Calendar.YEAR)
    return (currentYear downTo currentYear - 100).toList()
}