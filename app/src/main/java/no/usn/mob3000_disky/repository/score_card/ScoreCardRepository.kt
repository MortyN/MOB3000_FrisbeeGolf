package no.usn.mob3000_disky.repository.score_card

import no.usn.mob3000_disky.model.Arena
import no.usn.mob3000_disky.model.ArenaFilter
import no.usn.mob3000_disky.model.ScoreCard
import no.usn.mob3000_disky.model.ScoreCardFilter

interface ScoreCardRepository {

    suspend fun createScoreCard(scoreCard: ScoreCard, idToken: String): ScoreCard

    suspend fun getScoreCard(filter: ScoreCardFilter, idToken: String): List<ScoreCard>
}