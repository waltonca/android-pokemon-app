package com.example.pokemonapp.retrofit

import com.example.pokemonapp.models.Pokemon
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
    @GET("api/v2/pokemon/{pokemonName}")
    suspend fun getPokemon (
        @Query("pokemonName") pokemonName: String
    ): Pokemon

}