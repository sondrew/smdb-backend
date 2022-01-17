package com.backend.smdb.Services

import com.backend.smdb.Movie
import com.backend.smdb.SmdbRepository
import com.backend.smdb.models.MovieResponseModel
import com.backend.smdb.models.TMDbMovieResponseModel
import org.springframework.stereotype.Service

@Service
class SmdbService(val db: SmdbRepository, val tmdbService: TMDbService) {

    fun getFavourites(): List<MovieResponseModel> {
        val movies = db.findAll()
        return movies.map ( Movie::toResponseModel )
    }

    fun deleteFavourites() = db.deleteAll()

    fun movieExists(externalId: Int): Boolean = getMovie(externalId) == null

    fun getMovie(externalId: Int): Movie? = db.getMovie(externalId)

    fun saveMovie(externalId: Int) {
        val movie = getMovie(externalId)
        if (movie == null) {
            val streamProviders = tmdbService.getStreamingProvidersForNorway(externalId)
            val movieDetails = tmdbService.getMovieDetails(externalId)
            db.save(movieDetails.toDomainModel(streamProviders))
        }
    }

    fun updateMovie(updateMovie: TMDbMovieResponseModel) {
        val existingMovie = getMovie(updateMovie.id)
    }

    fun deleteMovie(externalId: Int) = db.deleteMovie(externalId)


}
