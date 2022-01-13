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

    fun saveMovie(externalId: Int) {
        val streamProviders = getStreamingProviders(externalId)
        val movieDetails = getMovieDetails(externalId)
        db.save(movieDetails.toDomainModel(streamProviders))
    }

    fun getMovieDetails(externalId: Int): MovieDetailsDto = gateway.getMovieDetails(externalId)

    fun getPopularMovies(): List<TMDbMovieResponseModel> {
        val response: TMDbMultipleMoviesDto = gateway.getPopularMovies()
        return response.results.map ( TMDbMovieDto::toResponseModel )
    }

    fun getStreamingProviders(externalId: Int): StreamCountryDto? {
        val providers = gateway.getStreamingProviders(externalId)
        if (providers.results?.containsKey("NO") == true) return providers.results["NO"]
        return null;
    }
}