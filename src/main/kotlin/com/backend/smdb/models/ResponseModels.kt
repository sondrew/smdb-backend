package com.backend.smdb.models

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
