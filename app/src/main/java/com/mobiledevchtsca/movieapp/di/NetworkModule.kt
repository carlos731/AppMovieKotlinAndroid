package com.mobiledevchtsca.movieapp.di

import com.mobiledevchtsca.movieapp.BuildConfig
import com.mobiledevchtsca.movieapp.data.api.ServiceApi
import com.mobiledevchtsca.movieapp.data.interceptor.ApiKeyInterceptor
import com.mobiledevchtsca.movieapp.data.interceptor.LanguageInterceptor
import com.mobiledevchtsca.movieapp.network.ServiceProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun providesApiKeyInterceptor(): ApiKeyInterceptor {
        return ApiKeyInterceptor()
    }

    @Singleton
    @Provides
    fun providesLanguageInterceptor(): LanguageInterceptor {
        return LanguageInterceptor()
    }

    @Singleton
    @Provides
    fun providesOkHttpClient(
        apiKeyInterceptor: ApiKeyInterceptor,
        languageInterceptor: LanguageInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor(apiKeyInterceptor)
            .addInterceptor(languageInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun providesRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun providesServiceApi(
        retrofit: Retrofit
    ): ServiceApi {
        return retrofit.create(ServiceApi::class.java)
    }

}