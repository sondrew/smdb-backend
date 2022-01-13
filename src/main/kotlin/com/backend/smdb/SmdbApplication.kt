package com.backend.smdb

import com.backend.smdb.models.MovieResponseModel
import com.backend.smdb.models.TMDbMovieResponseModel
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.*

@SpringBootApplication
class SmdbApplication

@RestController
class SmdbController(val service: SmdbService) {

	@GetMapping("/favourites")
	fun getAll(): List<MovieResponseModel> = service.getFavourites()

	@GetMapping("/save/{externalId}")
	fun saveMovie(@PathVariable externalId: Int) = service.saveMovie(externalId)

	@GetMapping("/discover")
	fun getPopularMovies(): List<TMDbMovieResponseModel> = service.getPopularMovies()
}

fun main(args: Array<String>) {
	runApplication<SmdbApplication>(*args)
}