package ir.jaamebaade.jaamebaade_client.model

data class VersePoemCategoriesPoet(
    /**
     * This field is nullable in cases that a verse is not referenced.
     */
    val verse: Verse?,
    val poem: Poem,
    val categories: List<Category>,
    val poet: Poet
)

fun VersePoemCategoriesPoet.toPathHeaderText(includePoemTitle: Boolean = true): String {
    val categoryPath = categories.joinToString(separator = " > ") { it.text }
    return if (includePoemTitle) {
        "$categoryPath > ${poem.title}"
    } else {
        categoryPath
    }
}
