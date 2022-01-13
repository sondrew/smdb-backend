package com.backend.smdb

import com.backend.smdb.models.TMDbMovieResponseModel
import java.util.HashMap

const val IMDB_BASE_URL = "https://www.imdb.com/title/"
const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w500/"

data class TMDbMultipleMoviesDto(
    val page: Int,
    val results: List<TMDbMovieDto>,
    val total_pages: Int,
    val total_results: Int
)

data class TMDbMovieDto(
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
) {
    fun toResponseModel(): TMDbMovieResponseModel =
        TMDbMovieResponseModel(
            id = id,
            title = title,
            voteAverage = vote_average,
            voteCount = vote_count,
            popularity = popularity,
            overview = overview,
            posterPath = "$POSTER_BASE_URL$poster_path",
            releaseDate = release_date,
        )
}

data class MovieDetailsDto(
    val id: Int,
    val title: String,
    val vote_average: Double,
    val vote_count: Int,
    val popularity: Double,
    val overview: String,
    val poster_path: String,
    val release_date: String,
    val genres: List<GenreDto>,
    val video: Boolean,
    val imdb_id: String
){
    data class GenreDto(val id: Int, val name: String)

    fun toDomainModel(providers: StreamCountryDto?): Movie =
        Movie(
            externalId = id,
            title = title,
            imdbUrl = "$IMDB_BASE_URL$imdb_id",
            posterUrl = "$POSTER_BASE_URL$poster_path",
            voteAverage = vote_average,
            voteCount = vote_count,
            popularity = popularity,
            overview = overview,
            releaseDate = release_date,
            genres = genres.map { it.name },
            platformStream = providers?.flatrate?.map { it.provider_name },
            platformBuy = providers?.buy?.map { it.provider_name }
        )
}



data class StreamResponseDto(
    val id: Int?,
    val results: HashMap<String, StreamCountryDto>?
)

data class StreamCountryDto(
    val link: String,
    val flatrate: List<StreamProviderDto>,
    val buy: List<StreamProviderDto>
)

data class StreamProviderDto(
    val provider_name: String
)