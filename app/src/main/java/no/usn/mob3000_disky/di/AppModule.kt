package no.usn.mob3000_disky.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import no.usn.mob3000_disky.App
import javax.inject.Singleton

// docs about singletons and di:
// https://developer.android.com/training/dependency-injection/hilt-android

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): App {
        return app as App
    }
}