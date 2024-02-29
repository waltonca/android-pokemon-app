package com.example.pokemonapp.models

data class Pokemon (
    val name: String,
    val types: Types,
    val height: Float,
    val weight: Float
)

data class Types (
    val type: Type
)

data class Type (
    val name: String,
    val url: String
)