package no.usn.mob3000_disky.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import no.usn.mob3000_disky.endpoints.*
import no.usn.mob3000_disky.repository.auth.AuthImplementation
import no.usn.mob3000_disky.repository.auth.AuthRepository
import no.usn.mob3000_disky.repository.myprofile.PostImplementation
import no.usn.mob3000_disky.repository.myprofile.PostRepository
import no.usn.mob3000_disky.repository.round.ArenaImplementation
import no.usn.mob3000_disky.repository.round.ArenaRepository
import no.usn.mob3000_disky.repository.score_card.ScoreCardImplementation
import no.usn.mob3000_disky.repository.score_card.ScoreCardRepository
import no.usn.mob3000_disky.repository.users.UserImplementation
import no.usn.mob3000_disky.repository.users.UserRepository
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun providePostRepository(
        postAPI: PostAPIService
    ): PostRepository {
        return PostImplementation(postAPI)
    }

    @Singleton
    @Provides
    fun provideArenaRepository(
        arenaApi: ArenaAPIService
    ): ArenaRepository {
        return ArenaImplementation(arenaApi)
    }

    @Singleton
    @Provides
    fun provideAuthRepository(
        authAPI: AuthAPIService
    ): AuthRepository {
        return AuthImplementation(authAPI)
    }

    @Singleton
    @Provides
    fun provideUserRepository(
        userApi: UserAPIService
    ): UserRepository {
        return UserImplementation(userApi)
    }

    @Singleton
    @Provides
    fun provideScoreCardRepository(
        scoreCardAPI: ScoreCardAPIService
    ): ScoreCardRepository {
        return ScoreCardImplementation(scoreCardAPI)
    }

}