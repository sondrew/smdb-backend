package com.backend.smdb

import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject

@Component
class TMDbGateway  {
    private final val apiBaseUrl = "https://api.themoviedb.org/3"
    private final val apiKey = "api_key=a72a25d115113ee281b71ef311503cd5"

    private inline fun <reified T> fetch(uri: String): T {
        return RestTemplate().getForObject(uri)
    }

    fun buildUri(path: String): String = "$apiBaseUrl$path${if (!path.contains("?")) "?" else "&"}$apiKey"

    fun getPopularMovies(): TMDbMultipleMoviesDto {
        val uri = buildUri("/discover/movie")
        return RestTemplate().getForObject(uri)
    }

    fun getStreamingProviders(externalId: Int): StreamResponseDto {
        val uri = buildUri("/movie/$externalId/watch/providers")
        return RestTemplate().getForObject(uri)
    }

    fun getMovieDetails(externalId: Int): MovieDetailsDto {
        val uri = buildUri("/movie/$externalId?")
        return fetch(uri)
    }


}