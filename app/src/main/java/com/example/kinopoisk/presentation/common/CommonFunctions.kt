package com.example.kinopoisk.presentation.common

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
    return lines.joinToString("\n")
}