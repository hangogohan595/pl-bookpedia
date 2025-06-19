package com.plcoding.bookpedia.book.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchedResponseDto(
    @SerialName("docs") val results: List<SearchedResponseDto>
)
