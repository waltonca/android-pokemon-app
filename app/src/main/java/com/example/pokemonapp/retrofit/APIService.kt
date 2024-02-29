package com.example.pokemonapp.retrofit

import com.example.pokemonapp.models.Pokemon
import retrofit2.http.GET
import retrofit2.http.Path


interface APIService {
    @GET("api/v2/pokemon/{name}")
    suspend fun getPokemonByName (@Path("name") pokemonName: String): Pokemon

}