package com.backend.smdb

import org.bson.types.ObjectId
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class SmdbApplication

@RestController
class SmdbController(val service: SmdbService) {

	@GetMapping("/favourites")
	fun getAll(): List<Movie> = service.getAllFavourites()

	@PostMapping("/favourites")
	fun saveMovie(@RequestBody movie: Movie) = service.saveMovie(movie)

	@GetMapping("/discover")
	fun getPopularMovies(): List<MovieEntity> = service.getPopularMovies()
}

fun main(args: Array<String>) {
	runApplication<SmdbApplication>(*args)
}