package com.backend.smdb.Repositories

import com.backend.smdb.RecommendationList
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface RecommendationListRepository : MongoRepository<RecommendationList, String> {

    //fun createRecommendationList(recommendationList: RecommendationList)

}
