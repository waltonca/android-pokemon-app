package com.example.pokemonapp

import android.net.http.HttpException
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresExtension
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonapp.models.Pokemon
import com.example.pokemonapp.retrofit.APIClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

class MainViewModel : ViewModel() {

    private val _pokemonStateFlow = MutableStateFlow<Pokemon?>(null)

    val pokemonStateFlow: StateFlow<Pokemon?> = _pokemonStateFlow

    fun updatePokemon(pokemonName: String) {
        viewModelScope.launch {
            // Add some code to check whether the response is correct
            try {
                val pokemon = APIClient.apiService.getPokemonByName(pokemonName)
                _pokemonStateFlow.value = pokemon
            } catch (e: Exception){
                // Network Problem
                e.printStackTrace()
                Log.i("testing" ,e.toString())

            }
        }
    }

}