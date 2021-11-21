package no.usn.mob3000_disky.repository.score_card

import no.usn.mob3000_disky.model.Arena
import no.usn.mob3000_disky.model.ArenaFilter
import no.usn.mob3000_disky.model.ScoreCard
import no.usn.mob3000_disky.model.ScoreCardFilter

interface ScoreCardRepository {

    suspend fun createScoreCard(scoreCard: ScoreCard): ScoreCard

    suspend fun getScoreCard(filter: ScoreCardFilter): List<ScoreCard>
}