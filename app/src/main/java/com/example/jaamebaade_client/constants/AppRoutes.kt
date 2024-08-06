package com.example.jaamebaade_client.constants

enum class AppRoutes(private val route: String) {
    DOWNLOADED_POETS_SCREEN("downloadedPoetsScreen"),
    DOWNLOADABLE_POETS_SCREEN("downloadablePoetsScreen"),
    POET_CATEGORY_SCREEN("poetCategoryScreen"),
    SETTINGS_SCREEN("settingsScreen"),
    SEARCH_SCREEN("searchScreen"),
    FAVORITE_SCREEN("favoriteScreen"),
    POEM("poem"),
    CHANGE_FONT_SCREEN("changeFontScreen"),
    ACCOUNT_SCREEN("accountScreen"),
    COMMENTS("comments"),
    CHANGE_THEME_SCREEN("changeThemeScreen")
    ;

    companion object {
        fun fromString(route: String): AppRoutes? {
            return entries.find { it.route == route }
        }
    }

    override fun toString() = route
}
