package com.backend.smdb.Services

import com.backend.smdb.*
import com.backend.smdb.Repositories.RecommendationListRepository
import com.backend.smdb.Repositories.SmdbRepository
import com.backend.smdb.models.CreateRecommendationListRequestModel
import com.backend.smdb.models.MovieResponseModel
import com.backend.smdb.models.RecommendationListResponseModel
import com.backend.smdb.models.TMDbMovieResponseModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Service

@Service
class SmdbService(val db: SmdbRepository, val recommendationDb: RecommendationListRepository, val tmdbService: TMDbService) {

    fun getFavourites(): List<MovieResponseModel> {
        val movies = db.findAll()
        return movies.map ( Movie::toResponseModel )
    }

    fun deleteFavourites() = db.deleteAll()



    fun movieExists(externalId: Int): Boolean = getMovie(externalId) == null

    fun getMovie(externalId: Int): Movie? = db.getMovie(externalId)

    fun saveMovie(externalId: Int) {
        val movie = getMovie(externalId)
        if (movie == null) {
            val streamProviders = tmdbService.getStreamingProvidersForNorway(externalId)
            val movieDetails = tmdbService.getMovieDetails(externalId)
            db.save(movieDetails.toDomainModel(streamProviders))
        }
    }

    fun updateMovie(updateMovie: TMDbMovieResponseModel) {
        val existingMovie = getMovie(updateMovie.id)
    }

    fun deleteMovie(externalId: Int) = db.deleteMovie(externalId)


    fun createRecommendationList(createList: CreateRecommendationListRequestModel): RecommendationListResponseModel? {
        if (createList.list.isEmpty()) return null

        val mediaDetails: List<RecommendedMedia> = runBlocking {
            val tvDetails: List<Deferred<RecommendedMedia>> =  createList.list.filter {it.mediaType == MediaType.tv} .map { async { tmdbService.getTVShowDetails(it.tmdbId).toRecommendedMedia(it) } }
            val movieDetails: List<Deferred<RecommendedMedia>> = createList.list.filter {it.mediaType == MediaType.movie} .map { async { tmdbService.getMovieDetails(it.tmdbId).toRecommendedMedia(it) } }
            return@runBlocking movieDetails.awaitAll() + tvDetails.awaitAll()
        }

        // db object
        val list = RecommendationList(
            listName = createList.listName,
            listDescription = createList.listDescription,
            list = mediaDetails
        )

        // check id does not exist already. if exist when creating new list, create new id. else edit existing using admin guid

        // sort list on passback
        return recommendationDb.save(list).toResponseModel()
    }

    fun deleteAllRecommendationLists() {
        recommendationDb.deleteAll()
    }

    fun getRecommendationList(listId: String): RecommendationListResponseModel? {
        val list = recommendationDb.findById(listId)
        if (list.isPresent) {
            return list.get().toResponseModel()
        }
        // TODO: trigger 404 or similar http response
        return null
    }

    // TODO: Currently get with admin guid, change to frontend model and/or authorize endpoint
    fun getAllRecommendations(): List<RecommendationList> = recommendationDb.findAll()


    /*
    fun getPopularMovies(): List<TMDbMovieResponseModel> {
        val response: TMDbMultipleMoviesDto = tmdbService.getPopularMovies()
        val response2: TMDbMultipleMoviesDto = tmdbService.getPopularMovies(2)
        val response3: TMDbMultipleMoviesDto = tmdbService.getPopularMovies(3)
        val favouritedMovieIds: List<Int> = getFavourites().map(MovieResponseModel::externalId)
        val allResponses: List<TMDbMovieDto> = response.results + response2.results + response3.results
        return allResponses.map { it.toResponseModel(it.hasBeenFavourited(favouritedMovieIds)) }
    }
     */
}
