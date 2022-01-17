package com.backend.smdb

import com.backend.smdb.Services.SmdbService
import com.backend.smdb.Services.TMDbService
import com.backend.smdb.models.MovieResponseModel
import com.backend.smdb.models.TMDbMovieResponseModel
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.*

@SpringBootApplication
class SmdbApplication

@RestController
class SmdbController(val dbService: SmdbService, val externalService: TMDbService) {

	@GetMapping("/favourites")
	fun getAll(): List<MovieResponseModel> = dbService.getFavourites()

	@GetMapping("/save/{externalId}")
	fun saveMovie(@PathVariable externalId: Int) = dbService.saveMovie(externalId)

    @GetMapping("/unsave/{externalId}")
    fun removeMovie(@PathVariable externalId: Int) = dbService.deleteMovie(externalId)

	@GetMapping("/discover")
	fun getPopularMovies(): List<TMDbMovieResponseModel> = externalService.getPopularMovies()

    @PutMapping("/update")
    fun updateMovie(@RequestBody movie: TMDbMovieResponseModel) = dbService

    @DeleteMapping("/nuke")
    fun deleteAllFavouriteMovies() = dbService.deleteFavourites()
}

fun main(args: Array<String>) {
	runApplication<SmdbApplication>(*args)
}
