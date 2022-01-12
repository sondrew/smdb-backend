package com.backend.smdb

import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject

@Service
class SmdbService(val db: SmdbRepository, val gateway: TMDbGateway) {

    fun getAll(): List<Movie> = db.findAll()

    fun getAllFavourites(): List<Movie> = db.findAll() //db.getFavourites()

    fun saveMovie(movie: Movie) = db.save(movie)

    fun getPopularMovies(): List<MovieEntity> {
        val response = gateway.getPopularMovies()
        return response.results

    }
}