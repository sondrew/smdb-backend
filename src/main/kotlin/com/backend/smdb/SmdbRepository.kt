package com.backend.smdb

import org.springframework.data.mongodb.repository.DeleteQuery
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface SmdbRepository : MongoRepository<Movie, String> {

    @Query("{ markedFavourite: true}")
    fun getFavourites(): List<Movie>

    @Query("{ externalId : ?0 }")
    fun getFavouriteMovie(externalId: Int): Movie?

    @DeleteQuery("{ externalId = ?0 }")
    fun deleteMovie(externalId: Int)
}
