package com.backend.smdb.models

import com.backend.smdb.MediaType

data class CreateRecommendationListRequestModel(
    val listName: String,
    val listDescription: String?,
    val list: List<CreateRecommendationListItemRequestModel>
    //val listId: String set when creating db id?
    //val createdBy: String? - probably not needed, but maybe some people want to set creator name
    //val adminId: UUID // backend - not sent to frontend
)

data class CreateRecommendationListItemRequestModel (
    val tmdbId: Int,
    val index: Int,
    val mediaType: MediaType,
    val userRating: Int? = null,
    val userComment: String? = null,
    //val originalTitle: String? = null,
    //val title: String,
    //val posterUrl: String, // get from backend
    //val imdbUrl: String, // backend
    //val genres: List<String> backend
)

/*
export interface CreateRecommendationList {
  listName: string;
  listDescription?: string;
  list: CreateRecommendationListItem[];
}

export interface CreateRecommendationListItem {
  tmdbId: number;
  index: number;
  mediaType: MediaType;
  userComment?: string | null;
  userRating?: number | null;
}
 */
