package com.backend.smdb

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface SmdbRepository : MongoRepository<Movie, String> {

    @Query("{ markedFavourite: true}")
    fun getFavourites(): List<Movie>
}
