package no.usn.mob3000_disky.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import no.usn.mob3000_disky.api.APIConstants
import no.usn.mob3000_disky.endpoints.MyProfileAPIService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Singleton
    @Provides
    fun provideMyProfileService(): MyProfileAPIService {
        return Retrofit.Builder()
            .baseUrl(APIConstants.APIHOST + APIConstants.APIPORT + APIConstants.APIPOSTPREFIX)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MyProfileAPIService::class.java)
    }

}