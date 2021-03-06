package no.usn.mob3000_disky.di

import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import no.usn.mob3000_disky.api.APIConstants
import no.usn.mob3000_disky.endpoints.*
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
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
    @Singleton
    @Provides
    fun provideAuthService(): AuthAPIService {
        return Retrofit.Builder()
            .baseUrl(APIConstants.APIHOST + APIConstants.APIPORT + APIConstants.APIUSERPREFIX)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthAPIService::class.java)
    }

    @Singleton
    @Provides
    fun provideFriendsService(): FriendsAPIService {
        return Retrofit.Builder()
            .baseUrl(APIConstants.APIHOST + APIConstants.APIPORT + APIConstants.APIUSERLINKPREFIX)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FriendsAPIService::class.java)
    }

    @Singleton
    @Provides
    fun provideScoreCardService(): ScoreCardAPIService {
        return Retrofit.Builder()
            .baseUrl(APIConstants.APIHOST + APIConstants.APIPORT + APIConstants.APISCORECARDPREFIX)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ScoreCardAPIService::class.java)
    }

    @Singleton
    @Provides
    fun provideAuthValidationService(): AuthValidationService {
        return Retrofit.Builder()
            .baseUrl(APIConstants.APIHOST + APIConstants.APIPORT)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(AuthValidationService::class.java)
    }




}