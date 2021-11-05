package no.usn.mob3000_disky.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import no.usn.mob3000_disky.endpoints.PostAPIService
import no.usn.mob3000_disky.repository.myprofile.PostImplementation
import no.usn.mob3000_disky.repository.myprofile.PostRepository
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

}