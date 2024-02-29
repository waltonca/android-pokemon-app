package com.example.pokemonapp

import android.net.http.HttpException
import android.os.Build
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

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun updatePokemon(pokemonName: String) {
        viewModelScope.launch {
            try {
                val pokemon = APIClient.APIService.getPokemonByName(pokemonName)
                // Update ViewModel state
                _pokemonStateFlow.value = pokemon
            } catch (e: IOException) {
                // Handle network error
                e.printStackTrace()
            } catch (e: HttpException) {
                // Handle HTTP error (e.g., 404 Not Found)
                e.printStackTrace()
            } catch (e: Exception) {
                // Handle other errors
                e.printStackTrace()

            }
        }
    }

}