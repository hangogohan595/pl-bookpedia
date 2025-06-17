package com.plcoding.bookpedia.book.presentation.book_list

import com.plcoding.bookpedia.book.domain.Book
import com.plcoding.bookpedia.core.presentation.UiText

data class BookListState(
    val searchQuery: String = "",
    val searchResults: List<Book> = books,
    val favoriteBooks: List<Book> = emptyList(),
    val isLoading: Boolean = false,
    val selectedTabIndex: Int = 0,
    val errorMessage: UiText? = null,
)

private val books = (1 .. 100).map {
    Book(
        id = it.toString(),
        title = "Book $it",
        imageUrl = "https://picsum.photos/200/300?random=$it",
        authors = listOf("Author $it"),
        description = "This is a description of Book $it.",
        languages = listOf("English"),
        firstPublishYear = null,
        averageRating = 4.67,
        ratingCount = 5,
        numPages = 100,
        numEditions = 3
    )
}