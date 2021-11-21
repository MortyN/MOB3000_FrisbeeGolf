package no.usn.mob3000_disky.endpoints

import no.usn.mob3000_disky.model.ScoreCard
import no.usn.mob3000_disky.model.ScoreCardFilter
import no.usn.mob3000_disky.model.User
import no.usn.mob3000_disky.model.UserFilter
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST

interface ScoreCardAPIService {

    @POST("create")
    suspend fun createScoreCard(@Body scoreCard: ScoreCard): ScoreCard

    @DELETE
    suspend fun deleteScoreCard(@Body scoreCardId: Long)

    @POST("get")
    suspend fun getScoreCard(@Body filter: ScoreCardFilter): List<ScoreCard>
}