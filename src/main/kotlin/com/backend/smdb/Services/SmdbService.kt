package com.backend.smdb.Services

import com.backend.smdb.Movie
import com.backend.smdb.SmdbRepository
import com.backend.smdb.TMDbMovieDto
import com.backend.smdb.TMDbMultipleMoviesDto
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


    fun getPopularMovies(): List<TMDbMovieResponseModel> {
        val response: TMDbMultipleMoviesDto = tmdbService.getPopularMovies()
        val response2: TMDbMultipleMoviesDto = tmdbService.getPopularMovies(2)
        val response3: TMDbMultipleMoviesDto = tmdbService.getPopularMovies(3)
        val favouritedMovieIds: List<Int> = getFavourites().map(MovieResponseModel::externalId)
        val allResponses: List<TMDbMovieDto> = response.results + response2.results + response3.results
        return allResponses.map { it.toResponseModel(it.hasBeenFavourited(favouritedMovieIds)) }
    }


}
