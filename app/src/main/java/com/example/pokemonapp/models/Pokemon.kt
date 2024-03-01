package com.example.pokemonapp.models

data class Pokemon (
    val name: String,
    val types: List<TypeItem>,
    val height: Float,
    val weight: Float,
    val sprites: Sprites
)

data class Sprites (
    val front_default: String
)

data class TypeItem (
    val slot: Int,
    val type: Type
)

data class Type (
    val name: String,
    val url: String
)

