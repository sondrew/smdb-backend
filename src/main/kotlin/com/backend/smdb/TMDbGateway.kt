package com.backend.smdb

import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject

@Component
class TMDbGateway  {
    private final val apiKey = "api_key=a72a25d115113ee281b71ef311503cd5"

    fun getPopularMovies(): TMBdResponseEntity {
        val uri = "https://api.themoviedb.org/3/discover/movie?$apiKey"
        val response = RestTemplate().getForObject<TMBdResponseEntity>(uri)
        return response
    }
}