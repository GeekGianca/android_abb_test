package com.example.nisumtest.di

import androidx.viewbinding.BuildConfig
import com.example.nisumtest.core.util.Common
import com.example.nisumtest.network.AbbreviationClient
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson =
        GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create()

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient.Builder =
        OkHttpClient.Builder().connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)

    @Singleton
    @Provides
    fun provideLoggingHeaders(): HttpLoggingInterceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.HEADERS)
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    @Singleton
    @Provides
    fun provideRetrofit(
        gson: Gson,
        okHttp: OkHttpClient.Builder,
        interceptor: HttpLoggingInterceptor
    ): Retrofit.Builder =
        Retrofit
            .Builder()
            .baseUrl(Common.API_URL)
            .client(okHttp.addInterceptor(interceptor).build())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addConverterFactory(ScalarsConverterFactory.create())

    @Singleton
    @Provides
    fun provideAbbreviationClient(retrofit: Retrofit.Builder): AbbreviationClient =
        retrofit
            .build()
            .create(AbbreviationClient::class.java)

}