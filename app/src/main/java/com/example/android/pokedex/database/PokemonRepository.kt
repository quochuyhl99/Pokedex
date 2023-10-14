package com.example.android.pokedex.database

import com.example.android.pokedex.model.Pokemon
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PokemonRepository(
    private val pokemonDao: PokemonDatabaseDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO) {

    suspend fun getAllPokemons(): List<Pokemon> {
        return withContext(ioDispatcher) {
            pokemonDao.getAllPokemons()
        }
    }

    suspend fun getPokemon(id: Long): Pokemon {
        return withContext(ioDispatcher) {
            pokemonDao.getPokemon(id)
        }
    }

    suspend fun savePokemon(pokemon: Pokemon) {
        withContext(ioDispatcher) {
            pokemonDao.savePokemon(pokemon)
        }
    }
}