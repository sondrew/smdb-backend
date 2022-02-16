package com.backend.smdb.models

import com.backend.smdb.MediaType
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import java.util.*
import javax.annotation.processing.Generated

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


data class RecommendationListResponseModel(
    // @Id @Generated val listId: UUID = UUID.randomUUID(),
    val id: String,
    val listName: String,
    val listDescription: String?,
    val list: List<RecommendedMediaResponseModel>
)

//@Document - maybe not needed when already inside RecommendationList?
data class RecommendedMediaResponseModel (
    val id: Int,
    val listIndex: Int, // maybe index is not needed? could maintain list index inside object without this. unsure if it's stored 1-to-1 in sequence
    //@Id val listId: String - should it have a unique ID in this list? or is it sufficient with index?
    val userRating: Int?,
    val userComment: String?,
    val title: String,
    val originalTitle: String?,
    val description: String?,
    val mediaType: MediaType,
    val posterPath: String?, // get from backend
    val imdbPath: String?, // backend
    val genres: List<String>
)
