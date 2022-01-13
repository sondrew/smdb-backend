package com.backend.smdb

import com.backend.smdb.models.MovieResponseModel
import com.mongodb.annotations.Immutable
import org.bson.types.ObjectId
import org.springframework.data.annotation.CreatedDate
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
