package no.usn.mob3000_disky.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import no.usn.mob3000_disky.endpoints.MyProfileAPIService
import no.usn.mob3000_disky.repository.myprofile.MyProfile_Impl
import no.usn.mob3000_disky.repository.myprofile.MyProfileRepository
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMyProfileRepository(
        myProfileAPI: MyProfileAPIService
    ): MyProfileRepository {
        return MyProfile_Impl(myProfileAPI)
    }

}