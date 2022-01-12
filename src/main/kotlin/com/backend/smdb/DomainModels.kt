package com.backend.smdb

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document


@Document
data class Movie(
    @Id val id: ObjectId = ObjectId.get(),
    val externalId: Int,
    val rating: Int,
    val comment: String = String(),
    val markedWatchlist: Boolean = false,
    val markedFavourite: Boolean = false,
    val lists: List<String> = listOf(),
    val platforms: List<String> = listOf()

)
