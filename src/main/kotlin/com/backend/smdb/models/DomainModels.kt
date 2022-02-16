package com.backend.smdb

import com.backend.smdb.models.MovieResponseModel
import com.backend.smdb.models.RecommendationListResponseModel
import com.backend.smdb.models.RecommendedMediaResponseModel
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*
import javax.annotation.processing.Generated


@Document
data class Movie(
    @Id @Generated val id: UUID = UUID.randomUUID(),
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
) {
    fun toResponseModel(): MovieResponseModel =
        MovieResponseModel(
            id = id.toString(),
            rating = rating,
            comment = comment,
            watchRank = watchRank,
            markedWatchlist = markedWatchlist,
            markedFavourite = markedFavourite,
            lists = lists,
            platformStream = platformStream,
            platformBuy = platformBuy,
            externalId = externalId,
            title = title,
            voteAverage = voteAverage,
            voteCount = voteCount,
            popularity = popularity,
            overview = overview,
            posterUrl = posterUrl,
            releaseDate = releaseDate,
            genres = genres
        )
}

@Document
data class RecommendationList(
    // @Id @Generated val listId: UUID = UUID.randomUUID(),
    @Id @Generated val id: String = UUID.randomUUID().toString().replace("-","").substring(0,8),
    val listName: String,
    val listDescription: String?,
    val list: List<RecommendedMedia>,
    val adminId: UUID = UUID.randomUUID() // to not pass to frontend model
){
    fun toResponseModel(): RecommendationListResponseModel =
        RecommendationListResponseModel(
            id,
            listName,
            listDescription,
            list.map { RecommendedMediaResponseModel(
                it.id,
                it.listIndex,
                it.userRating,
                it.userComment,
                it.title,
                it.originalTitle,
                it.description,
                it.mediaType,
                it.posterPath,
                it.imdbPath,
                it.genres
            ) }.sortedBy { it.listIndex }
        )
}

//@Document - maybe not needed when already inside RecommendationList?
data class RecommendedMedia (
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

enum class MediaType {tv, movie, person}
