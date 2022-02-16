package com.backend.smdb

import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject

@Component
class TMDbGateway  {
    private final val apiBaseUrl = "https://api.themoviedb.org/3"
    private final val apiKey = "api_key=a72a25d115113ee281b71ef311503cd5"

    fun buildUri(path: String): String = "$apiBaseUrl$path${if (!path.contains("?")) "?" else "&"}$apiKey"

    private inline fun <reified T> fetch(uri: String): T {
        return RestTemplate().getForObject(uri)
    }

    fun getPopularMovies(page: Int): TMDbMultipleMoviesDto {
        val uri = buildUri("/discover/movie?page=$page")
        return fetch(uri)
    }

    fun getStreamingProviders(externalId: Int): StreamResponseDto {
        val uri = buildUri("/movie/$externalId/watch/providers")
        return fetch(uri)
    }

    fun getMovieDetails(externalId: Int): MovieDetailsDto {
        val uri = buildUri("/movie/$externalId?")
        return fetch(uri)
    }

    fun getTVShowDetails(externalId: Int, withProviders: Boolean = false): TVDetailsDto {
        val uri = buildUri("/tv/$externalId${if (withProviders) "&append_to_response=watch/providers" else ""}")
        return fetch(uri)
    }

    fun getMoviesWithProviders(providers: List<Int>): TMDbMultipleMoviesDto {
        val uri = buildUri("/movie/discover?&watch_region=NO&with_watch_providers=${providers.joinToString("")}")
        return fetch(uri)
    }

    fun searchMulti(query: String): TMDbMultiSearchDto {
        val uri = buildUri("/search/multi?query=$query")
        return fetch(uri)
    }


}
