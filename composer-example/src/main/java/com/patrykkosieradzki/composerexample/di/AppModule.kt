package com.patrykkosieradzki.composerexample.di

import com.patrykkosieradzki.composerexample.AppConfiguration
import com.patrykkosieradzki.composerexample.ComposerAppConfiguration
import com.patrykkosieradzki.composerexample.navigation.ComposerNavigator
import com.patrykkosieradzki.composerexample.navigation.ComposerNavigatorImpl
import com.patrykkosieradzki.composerexample.repositories.CoinApiRepository
import com.patrykkosieradzki.composerexample.repositories.CoinRepository
import com.patrykkosieradzki.composerexample.services.CoinRankingService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideAppConfiguration(): AppConfiguration {
        return ComposerAppConfiguration()
    }

    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        appConfiguration: AppConfiguration,
        moshi: Moshi,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(appConfiguration.apiUrl)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideCoinRankingService(retrofit: Retrofit): CoinRankingService {
        return retrofit.create(CoinRankingService::class.java)
    }

    @Singleton
    @Provides
    fun provideCoinRepository(coinRankingService: CoinRankingService): CoinRepository {
        return CoinApiRepository(coinRankingService)
    }

    @Singleton
    @Provides
    fun provideComposerNavigator(): ComposerNavigator {
        return ComposerNavigatorImpl()
    }
}
