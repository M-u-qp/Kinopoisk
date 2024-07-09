package com.example.kinopoisk.presentation.common

enum class TitleCollections(val value: String) {
    TOP_POPULAR_MOVIES("Популярные фильмы"),
    POPULAR_SERIES("Сериалы")
}

enum class TitleCollectionsDB(val value: String) {
    READY_TO_VIEW("Хочу посмотреть"),
    FAVORITE("Понравилось"),
    VIEWED("Просмотрено"),
    INTERESTING("Вам было интересно")
}

enum class TypeGalleryRequest(val value: String) {
    STILL("Кадры"),
    SHOOTING("Со съемок"),
    FAN_ART("Фан-арты"),
    CONCEPT("Концепт-арты")
}

enum class TypeSearchFilter(val value: String) {
    ALL("Все"),
    FILM("Фильмы"),
    TV_SERIES("Сериалы")
}

enum class SortSearchFilter(val value: String) {
    RATING("Рейтинг"),
    NUM_VOTE("Популярной"),
    YEAR("Дата")
}