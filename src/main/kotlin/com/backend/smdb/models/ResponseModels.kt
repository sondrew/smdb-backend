package com.backend.smdb.models

import com.backend.smdb.MediaType
import org.springframework.data.annotation.LastModifiedDate

data class MovieResponseModel(
    // Internal data
    val id: String,
    val comment: String,
    val rating: Int?,
    val watchRank: Int?,
    val markedWatchlist: Boolean,
    val markedFavourite: Boolean,
    val lists: List<String>,
    val platformStream: List<String>?,
    val platformBuy: List<String>?,
    // TMDb data
    val externalId: Int,
    val title: String,
    val voteAverage: Double,
    val voteCount: Int,
    val popularity: Double,
    val overview: String,
    val posterUrl: String,
    val releaseDate: String,
    val genres: List<String>
)


data class TMDbMovieResponseModel(
    val id: Int,
    val title: String,
    val voteAverage: Double,
    val voteCount: Int,
    val popularity: Double,
    val overview: String,
    val posterUrl: String,
    val releaseDate: String,
    val markedFavourite: Boolean
)

data class SearchResponseModel(
    val id: Int,
    val title: String,
    val mediaType: MediaType,
    val voteAverage: Double,
    val voteCount: Int,
    val popularity: Double,
    val overview: String,
    val posterUrl: String,
    val releaseDate: String,
    val originalTitle: String
)

data class MovieRequestModel(
    val rating: Int? = null,
    val comment: String = String(),
    val watchRank: Int? = null,
    val markedWatchlist: Boolean = false,
    val markedFavourite: Boolean = false,
    val lists: List<String> = listOf(),
    val platformStream: List<String>? = listOf(),
    val platformBuy: List<String>? = listOf(),
    @LastModifiedDate val modifiedDate: LastModifiedDate = LastModifiedDate(),
    // External
    val externalId: Int,
    val title: String,
    val imdbUrl: String,
    val posterUrl: String,
    val voteAverage: Double,
    val voteCount: Int,
    val popularity: Double,
    val overview: String,
    val releaseDate: String,
    val genres: List<String>
)
