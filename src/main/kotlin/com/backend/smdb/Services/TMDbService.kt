package com.backend.smdb.Services

import com.backend.smdb.*
import com.backend.smdb.models.SearchResponseModel
import com.backend.smdb.models.TMDbMovieResponseModel
import org.springframework.stereotype.Service

@Service
class TMDbService(val gateway: TMDbGateway) {
    fun getMovieDetails(externalId: Int): MovieDetailsDto = gateway.getMovieDetails(externalId)

    fun getPopularMovies(page: Int = 1): TMDbMultipleMoviesDto = gateway.getPopularMovies(page)

    fun discoverMoviesWithProviders(providers: List<Int>): List<TMDbMovieResponseModel> {
        val response: TMDbMultipleMoviesDto = gateway.getMoviesWithProviders(providers)
        return response.results.map(TMDbMovieDto::toResponseModel)
    }

    fun getStreamingProvidersForNorway(externalId: Int): StreamCountryDto? {
        val providers = gateway.getStreamingProviders(externalId)
        if (providers.results?.containsKey("NO") == true) return providers.results["NO"]
        return null;
    }

    fun searchMulti(query: String) : List<SearchResponseModel> {
        // sanitize query? handle error
        val result = gateway.searchMulti(query)
        if (result.total_results == 0) return emptyList()

        val moviesAndShows = result.results.filter { it.media_type == MediaType.tv || it.media_type == MediaType.movie }
        return moviesAndShows.map ( SearchResultDto::toResponseModel )
    }
}
