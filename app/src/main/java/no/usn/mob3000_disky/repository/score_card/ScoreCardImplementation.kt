package no.usn.mob3000_disky.repository.score_card

import no.usn.mob3000_disky.endpoints.ArenaAPIService
import no.usn.mob3000_disky.endpoints.ScoreCardAPIService
import no.usn.mob3000_disky.model.Arena
import no.usn.mob3000_disky.model.ArenaFilter
import no.usn.mob3000_disky.model.ScoreCard
import no.usn.mob3000_disky.model.ScoreCardFilter
import no.usn.mob3000_disky.repository.round.ArenaRepository

class ScoreCardImplementation(
    private val scoreCardAPI: ScoreCardAPIService
): ScoreCardRepository {

    override suspend fun getScoreCard(filter: ScoreCardFilter): List<ScoreCard> {
       return scoreCardAPI.getScoreCard(filter)
    }


}