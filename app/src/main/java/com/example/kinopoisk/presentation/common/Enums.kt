package com.example.kinopoisk.presentation.common

enum class TitleCollections(val value: String) {
    PREMIERES("Премьеры"),
    TOP_POPULAR_ALL("Популярное"),
    TOP_250("Топ-250"),
    SERIALS("Сериалы")
}

enum class TitleCollectionsDB(val value: String) {
    READY_TO_VIEW("Хочу посмотреть"),
    FAVORITE("Понравилось"),
    VIEWED("Просмотрено")
}

enum class TypeGalleryRequest(val value: String) {
    STILL("Кадры"),
    SHOOTING("Со съемок"),
    FAN_ART("Фан-арты"),
    CONCEPT("Концепт-арты")
}