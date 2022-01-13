package com.backend.smdb

import com.backend.smdb.models.TMDbMovieResponseModel

const val IMDB_BASE_URL = "https://www.imdb.com/title/"
const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w500/"

data class TMDbMultipleMoviesDto(
    val page: Int,
    val results: List<TMDbMovieDto>,
    val total_pages: Int,
    val total_results: Int
)

data class TMDbMovieDto(
    val id: Int,
    val title: String,
    val vote_average: Double,
    val vote_count: Int,
    val popularity: Double,
    val overview: String,
    val poster_path: String,
    val release_date: String,
    val genre_ids: List<Int>,
    val video: Boolean
) {
    fun toResponseModel(favourited: Boolean = false): TMDbMovieResponseModel =
        TMDbMovieResponseModel(
            id = id,
            title = title,
            voteAverage = vote_average,
            voteCount = vote_count,
            popularity = popularity,
            overview = overview,
            posterUrl = "$POSTER_BASE_URL$poster_path",
            releaseDate = release_date,
            markedFavourite = favourited
        )

    fun hasBeenFavourited(externalIds: List<Int>): Boolean = externalIds.contains(id)

}

data class MovieDetailsDto(
    val id: Int,
    val title: String,
    val vote_average: Double,
    val vote_count: Int,
    val popularity: Double,
    val overview: String,
    val poster_path: String,
    val release_date: String,
    val genres: List<GenreDto>,
    val video: Boolean,
    val imdb_id: String
){
    data class GenreDto(val id: Int, val name: String)

    fun toDomainModel(providers: StreamCountryDto?): Movie =
        Movie(
            externalId = id,
            title = title,
            markedFavourite = true,
            imdbUrl = "$IMDB_BASE_URL$imdb_id",
            posterUrl = "$POSTER_BASE_URL$poster_path",
            voteAverage = vote_average,
            voteCount = vote_count,
            popularity = popularity,
            overview = overview,
            releaseDate = release_date,
            genres = genres.map { it.name },
            platformStream = providers?.flatrate?.map { it.provider_name },
            platformBuy = providers?.buy?.map { it.provider_name }
        )
}



data class StreamResponseDto(
    val id: Int?,
    val results: LinkedHashMap<String, StreamCountryDto>?
)

data class StreamCountryDto(
    val link: String?,
    val flatrate: List<StreamProviderDto>?,
    val buy: List<StreamProviderDto>?
)

data class StreamProviderDto(
    val provider_name: String
)

val watchProviders = mapOf(
    "Netflix" to 8,
    "Prime Video" to 119,
    "Prime Video" to 9,
    "Amazon Video" to 10,
    "IMDb TV Amazon Channel" to 613,
    "Disney+" to 337,
    "Hotstar Disney+" to 377,
    "Google Play Movies" to 3,
    "C More" to 77,
    "Paramount Plus" to 531,
    "HBO Max" to 384,
    "HBO" to 118,
    "Rakuten TV" to 35,
    "Apple TV+" to 350,
    "Youtube" to 192,
    "Disney Plus" to 390,
    "NRK TV" to 442,
    "SF Anytime" to 426,
    "TV2 Play" to 431,
    "SVT" to 493,
    "Viafree" to 494,
    "Viaplay" to 76,
    "Youtube Premium" to 188,
    "Strim" to 578,
    "Telia Play" to 553,
    "SVT Player" to 593,
    "Syfy" to 215,
    "Discovery+" to 520,
    "Plex" to 538
)

// TODO: Introduce "ignore movie" functionality to not never suggest that movie/show again
/*
TODO Feature: Super simple user interface to create lists to recommend movies to friends
    Landing page just a simple explanation and a "Create list" button/link (alternatively also search field)
    Create title for list (alternatively also private description for your own memory) and add movies/shows
        To recommend movies and shows to friends - Create list with recommended/favourite movies
        Input/detect client country and give them option to mark which services should be included
        Integration with google/twitter to be able to store
        If connected with user system - choose sharing level; private, URL, user, public, etc
        Litt søkt, men: create "target group" ('interest x'/age group/family or friends/) for when creating many list
            Or maybe not its own functionality, but instead suggestion for "lists of lists"
            Though doubtful people will create that many lists that it will be necessary
    Give option to connect newly created link/list to google/fb/etc account - to be able to change/remove/view list(s)
        Or just give them "admin" link to change/delete it later - security concerns though not vital data
        Alternatively give option to delete list in X days, or email me destruction/editing link
        User profile to manage multiple lists
    "Follow" person's list
    Minimal/basic analytics - ingen cookie consent eller mengder av ghostery trackers
    Se på noen passende TLDer kombinert med nettside navn
    recommend.movie ? f.eks.
    Full page SPA, updates and animates to new functionality when pressing button

TODO Feature idea: Discover new shows movies based on which streaming services you have
    See other's lists but filtered to only what you have9
    See recommendations within a service and genre - instantaneous toggling of all parameters and filters
    Personalized recommendation


Playpilot har noe lignende? Garantert ikke like simpelt, but still
 */
