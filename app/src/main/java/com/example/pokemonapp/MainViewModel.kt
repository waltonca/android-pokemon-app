package com.example.pokemonapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonapp.models.Pokemon
import com.example.pokemonapp.retrofit.APIClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _pokemonStateFlow = MutableStateFlow<Pokemon?>(null)

    val pokemonStateFlow: StateFlow<Pokemon?> = _pokemonStateFlow.asStateFlow()

    fun updatePokemon(pokemonName: String) {
        viewModelScope.launch {
            val pokemon = APIClient.APIService.getPokemon(pokemonName)

            // Update ViewModel state
            _pokemonStateFlow.value = pokemon
        }
    }

}