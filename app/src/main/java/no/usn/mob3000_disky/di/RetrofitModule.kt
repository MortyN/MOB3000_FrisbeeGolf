package no.usn.mob3000_disky.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import no.usn.mob3000_disky.api.APIConstants
import no.usn.mob3000_disky.endpoints.ArenaAPIService
import no.usn.mob3000_disky.endpoints.PostAPIService
import no.usn.mob3000_disky.endpoints.UserAPIService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Singleton
    @Provides
    fun provideFeedService(): PostAPIService {
        return Retrofit.Builder()
            .baseUrl(APIConstants.APIHOST + APIConstants.APIPORT + APIConstants.APIPOSTPREFIX)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PostAPIService::class.java)
    }

    @Singleton
    @Provides
    fun provideArenaService(): ArenaAPIService {
        return Retrofit.Builder()
            .baseUrl(APIConstants.APIHOST + APIConstants.APIPORT + APIConstants.APIARENAPREFIX)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ArenaAPIService::class.java)
    }

    @Singleton
    @Provides
    fun provideUserService(): UserAPIService {
        return Retrofit.Builder()
            .baseUrl(APIConstants.APIHOST + APIConstants.APIPORT + APIConstants.APIUSERPREFIX)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserAPIService::class.java)
    }

}