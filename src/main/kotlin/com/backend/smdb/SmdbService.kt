package com.backend.smdb

import com.backend.smdb.models.MovieResponseModel
import com.backend.smdb.models.TMDbMovieResponseModel
import org.springframework.stereotype.Service

@Service
class SmdbService(val db: SmdbRepository, val gateway: TMDbGateway) {

    fun getFavourites(): List<MovieResponseModel> {
        val movies = db.findAll()
        return movies.map ( Movie::toResponseModel )
    }

    fun deleteFavourites() = db.deleteAll()

    fun movieExists(externalId: Int): Boolean = getFavouriteMovie(externalId) == null

    fun getFavouriteMovie(externalId: Int): Movie? = db.getFavouriteMovie(externalId)

    fun saveMovie(externalId: Int) {
        val movie = getFavouriteMovie(externalId)
        if (movie == null) {
            val streamProviders = getStreamingProvidersForNorway(externalId)
            val movieDetails = getMovieDetails(externalId)
            db.save(movieDetails.toDomainModel(streamProviders))
        }
    }

    fun deleteMovie(externalId: Int) = db.deleteMovie(externalId)

    fun getMovieDetails(externalId: Int): MovieDetailsDto = gateway.getMovieDetails(externalId)

    fun getPopularMovies(): List<TMDbMovieResponseModel> {
        val response: TMDbMultipleMoviesDto = gateway.getPopularMovies()
        val response2: TMDbMultipleMoviesDto = gateway.getPopularMovies(2)
        val response3: TMDbMultipleMoviesDto = gateway.getPopularMovies(3)
        val allResponses: List<TMDbMovieDto> = response.results + response2.results + response3.results
        return allResponses.map ( TMDbMovieDto::toResponseModel )
    }

    fun discoverMoviesWithProviders(providers: List<Int>) : List<TMDbMovieResponseModel> {
        val response: TMDbMultipleMoviesDto = gateway.getMoviesWithProviders(providers)
        return response.results.map ( TMDbMovieDto::toResponseModel )
    }

    fun getStreamingProvidersForNorway(externalId: Int): StreamCountryDto? {
        val providers = gateway.getStreamingProviders(externalId)
        if (providers.results?.containsKey("NO") == true) return providers.results["NO"]
        return null;
    }
}
