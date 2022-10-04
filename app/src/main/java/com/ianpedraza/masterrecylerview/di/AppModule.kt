package com.ianpedraza.masterrecylerview.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ianpedraza.masterrecylerview.BuildConfig
import com.ianpedraza.masterrecylerview.data.pokemon.api.PokemonApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        // val logging = HttpLoggingInterceptor()
        // logging.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder()
        // httpClient.addInterceptor(logging)
        return httpClient.build()
    }

    @Singleton
    @Provides
    fun providesGsonBuilder(): Gson = GsonBuilder()
        .excludeFieldsWithoutExposeAnnotation()
        .create()

    @Singleton
    @Provides
    fun provideRetrofit(
        gson: Gson,
        client: OkHttpClient
    ): Retrofit = Retrofit
        .Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(client)
        .build()

    /* Get Pokemon */

    @Singleton
    @Provides
    fun providePokemonApi(
        retrofit: Retrofit
    ): PokemonApi = retrofit.create(PokemonApi::class.java)

    /*
    @Singleton
    @Provides
    fun providePokemonMapper() = PokemonMapper()

    @Singleton
    @Provides
    fun providePokemonRepository(
        pokemonService: PokemonService,
        mapper: PokemonMapper
    ) = PokemonRepository(pokemonService, mapper)
    */
}
