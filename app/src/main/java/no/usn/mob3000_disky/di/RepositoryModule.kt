package no.usn.mob3000_disky.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import no.usn.mob3000_disky.endpoints.ArenaAPIService
import no.usn.mob3000_disky.endpoints.AuthAPIService
import no.usn.mob3000_disky.endpoints.PostAPIService
import no.usn.mob3000_disky.repository.auth.AuthImplementation
import no.usn.mob3000_disky.repository.auth.AuthRepository
import no.usn.mob3000_disky.repository.myprofile.PostImplementation
import no.usn.mob3000_disky.repository.myprofile.PostRepository
import no.usn.mob3000_disky.repository.round.ArenaImplementation
import no.usn.mob3000_disky.repository.round.ArenaRepository
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

}