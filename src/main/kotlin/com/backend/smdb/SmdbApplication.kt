package com.backend.smdb

import com.backend.smdb.Services.SmdbService
import com.backend.smdb.Services.TMDbService
import com.backend.smdb.models.*
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
	fun getPopularMovies(): List<TMDbMovieResponseModel> = dbService.getPopularMovies()

    @PutMapping("/update")
    fun updateMovie(@RequestBody movie: TMDbMovieResponseModel) = dbService

    @DeleteMapping("/nuke")
    fun deleteAllFavouriteMovies() = dbService.deleteFavourites()

    @GetMapping("/search/{query}")
    fun searchMoviesAndTV(@PathVariable query: String): List<SearchResponseModel> =
        externalService.searchMulti(query)

    @GetMapping("/details")
    fun getMultipleMovieDetails(@RequestParam("movie") movies: List<Int>): List<MovieDetailsDto> = externalService.getMultipleMovieDetails(movies)

    @PostMapping("/create")
    fun createRecommendationList(@RequestBody recommendationList: CreateRecommendationListRequestModel): RecommendationListResponseModel? =
        dbService.createRecommendationList(recommendationList)

    @DeleteMapping("/nuke/lists") // TODO: Authorize or remove
    fun deleteAllRecommendationLists() = dbService.deleteAllRecommendationLists()

    @GetMapping("/recommendations") // TODO: Authorize or remove
    fun getAllRecommendationLists() = dbService.getAllRecommendations()

    @GetMapping("/list/{listId}") // validate list id - if not on correct format, return invalid
    fun getRecommendationList(@PathVariable listId: String): RecommendationListResponseModel? = dbService.getRecommendationList(listId)

}

fun main(args: Array<String>) {
	runApplication<SmdbApplication>(*args)
}
