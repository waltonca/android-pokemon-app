package com.example.pokemonapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.pokemonapp.models.Pokemon
import com.example.pokemonapp.ui.theme.PokemonAppTheme
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {

    private lateinit var mainViewModel: MainViewModel

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize ViewModel
        mainViewModel = MainViewModel()

        // Load the pokemon from API
        mainViewModel.updatePokemon("pikachu2222s")

        setContent {
            PokemonAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DisplayCurrentPokemon()

                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun DisplayCurrentPokemon() {
        //
        // Get pokemon from ViewModel, and the UI will re-compose when ViewModel changes or pokemon data is loaded
        //

        val pokemon by mainViewModel.pokemonStateFlow.collectAsState()

        val pokemonName = pokemon?.name
        val pokemonHeight = pokemon?.height
        val pokemonWeight = pokemon?.weight

        // pokemonTypes can have 1 type or more than 1 types
        val pokemonTypes: List<String>? = pokemon?.types?.map { it.type.name }
        // I need put them together in one string.
        val pokemonTypesString: String = pokemonTypes?.joinToString(", ") ?: "Unknown"

        //
        // Render UI
        //

        var textFieldPokemonName by remember { mutableStateOf("") }

        Scaffold(
            topBar = {
                // Don't need
            },
        ) { innerPadding ->

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Logo of pokomon
                    Box(
                        modifier = Modifier
                            .padding(top = 20.dp, bottom = 20.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription = "logo",
                            modifier = Modifier
                                .size(348.dp, 128.dp)
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally)
                            .background(Color.Transparent)
                            .padding(bottom = 30.dp, start = 20.dp, end = 20.dp)
                    ) {
                        // Update name Text Field
                        TextField(
                            value = textFieldPokemonName,
                            onValueChange = { textFieldPokemonName = it },
                            label = { Text("Pokemon Name") },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                disabledContainerColor = Color.Transparent,
                            ),
                            modifier = Modifier
                                .weight(1f)
                        )

                        Button(
                            onClick = {
                                //name should be lowerCase
                                val lowercasePokemonName = textFieldPokemonName.lowercase()
                                mainViewModel.updatePokemon(lowercasePokemonName)
                            },
                            modifier = Modifier.padding(start = 8.dp)
                        ) {
                            Text("Go")
                        }
                    }

                    // Add a condition statement here
                    // Only the right pokemon name render the following UI
                    // I should put bellow into a function

                    if (pokemon != null){
                        // pokemon icon (using Coil)
                        val imgUrl = pokemon?.sprites?.front_default
                        AsyncImage(
                            model = imgUrl,
                            contentDescription = "Current pokemon image",
                            modifier = Modifier.size(128.dp)
                        )

                        // pikachu
                        Text(
                            pokemonName.toString(),
                            fontSize = 40.sp
                        )

                        // Types: electric
                        Text(
                            "Types: " + pokemonTypesString,
                            fontSize = 30.sp
                        )

                        // Height: 4ft
                        Text(
                            "Height: ${pokemonHeight?.roundToInt()}ft",
                            fontSize = 20.sp
                        )

                        // Weight: 60lbs
                        Text(
                            "Weight: ${pokemonWeight?.roundToInt()}lbs",
                            fontSize = 20.sp
                        )
                    } else {
                       Text(text = "Not found")
                    }

                }
            }
        }
    }
}

