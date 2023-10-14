package com.example.android.pokedex.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.android.pokedex.model.Pokemon

@Dao
interface PokemonDatabaseDao {
    @Query("SELECT * FROM pokemon_table")
    suspend fun getAllPokemons(): List<Pokemon>

    @Query("SELECT * FROM pokemon_table WHERE id = :id")
    suspend fun getPokemon(id: Long): Pokemon

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePokemon(pokemon: Pokemon)
}