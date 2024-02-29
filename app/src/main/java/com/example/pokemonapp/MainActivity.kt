package com.example.pokemonapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
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
        mainViewModel.updatePokemon("pikachu")

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
    fun DisplayCurrentPokemon(){
        //
        // Get pokemon from ViewModel, and the UI will re-compose when ViewModel changes or pokemon data is loaded
        //

        val pokemon by mainViewModel.pokemonStateFlow.collectAsState()

        val pokemonName = pokemon?.name
        val pokemonType = pokemon?.types?.type
        val pokemonHeight = pokemon?.height
        val pokemonWeight = pokemon?.weight

        //
        // Render UI
        //

        var textFieldPokemonName by remember { mutableStateOf("") }

        Scaffold(
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    title = {
                        // Current location
                        Text("${pokemonName}, ${pokemonType?.name}, ${pokemonWeight}, ${pokemonHeight}",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis)
                    }
                )
            },
        ) { innerPadding ->

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ){
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ){

                    // Update Location Text Field
                    TextField(
                        value = textFieldPokemonName,
                        onValueChange = { textFieldPokemonName = it },
                        label = { Text("Pokemon Name") }
                    )

                    Button(
                        onClick = {
                            mainViewModel.updatePokemon(textFieldPokemonName)
                        }
                    ){
                        Text("Go")
                    }


                    // pokemon icon (using Coil)
                    val imgUrl = pokemon?.sprites?.front_default
                    AsyncImage(
                        model = imgUrl,
                        contentDescription = "Current pokemon image",
                        modifier = Modifier.size(128.dp))

                    // pikachu
                    Text(pokemonName.toString(),
                        fontSize = 30.sp)

                    // Types: electric

                    // Height: 4ft
                    Text("${pokemonHeight?.roundToInt()}ft",
                        fontSize = 20.sp)

                    // Weight: 60lbs
                    Text("Weight: ${pokemonWeight?.roundToInt()}lbs",
                        fontSize = 20.sp)


                }
            }
        }
    }
}

