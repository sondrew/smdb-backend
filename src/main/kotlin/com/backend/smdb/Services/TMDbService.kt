package com.backend.smdb.Services

import com.backend.smdb.*
import com.backend.smdb.models.MovieResponseModel
import com.backend.smdb.models.TMDbMovieResponseModel
import org.springframework.stereotype.Service

@Service
class TMDbService(val gateway: TMDbGateway, val smdbService: SmdbService) {
    fun getMovieDetails(externalId: Int): MovieDetailsDto = gateway.getMovieDetails(externalId)

    fun getPopularMovies(): List<TMDbMovieResponseModel> {
        val response: TMDbMultipleMoviesDto = gateway.getPopularMovies()
        val response2: TMDbMultipleMoviesDto = gateway.getPopularMovies(2)
        val response3: TMDbMultipleMoviesDto = gateway.getPopularMovies(3)
        val favouritedMovieIds: List<Int> = smdbService.getFavourites().map(MovieResponseModel::externalId)
        val allResponses: List<TMDbMovieDto> = response.results + response2.results + response3.results
        return allResponses.map { it.toResponseModel(it.hasBeenFavourited(favouritedMovieIds)) }
    }

    fun discoverMoviesWithProviders(providers: List<Int>): List<TMDbMovieResponseModel> {
        val response: TMDbMultipleMoviesDto = gateway.getMoviesWithProviders(providers)
        return response.results.map(TMDbMovieDto::toResponseModel)
    }

    fun getStreamingProvidersForNorway(externalId: Int): StreamCountryDto? {
        val providers = gateway.getStreamingProviders(externalId)
        if (providers.results?.containsKey("NO") == true) return providers.results["NO"]
        return null;
    }

    fun searchMulti(query: String) : List<TMDbMovieResponseModel> {
        val result = gateway.searchMulti(query)
        if (result.total_results == 0) return emptyList()

        val moviesAndShows = result.results.filter { it.media_type == "tv" || it.media_type == "movie"}
        return moviesAndShows.map ( TMDbMovieDto::toResponseModel )
    }
}
