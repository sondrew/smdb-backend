package com.backend.smdb

import java.text.SimpleDateFormat

data class TMBdResponseEntity(
    val page: Int,
    val results: List<MovieEntity>,
    val total_pages: Int,
    val total_results: Int
)

data class MovieEntity(
    val id: Int,
    val title: String,
    val vote_average: Double,
    val vote_count: Int,
    val popularity: Double,
    val overview: String,
    val poster_path: String,
    val release_date: String,
    val genre_ids: List<Int>,
    val video: Boolean

    )
