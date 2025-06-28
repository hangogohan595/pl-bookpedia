package com.plcoding.bookpedia.book.presentation.book_detail

import com.plcoding.bookpedia.book.domain.Book
import com.plcoding.bookpedia.core.presentation.UiText

data class BookDetailState(
    val isLoading: Boolean = false,
    val isFavorite: Boolean = false,
    val book: Book? = null,
    val errorMessage: UiText? = null,
)