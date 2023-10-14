package com.example.android.pokedex.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "pokemon_table")
data class Pokemon(
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    val baseExperience: Int,
    val height: Int,
    val name: String,
    val weight: Int,
    val frontDefault: String,
    val frontShiny: String,
    val hp: Int,
    val attack: Int,
    val defense: Int,
    val specialAttack: Int,
    val specialDefence: Int,
    val speed: Int,
    @field:TypeConverters(TypesConverter::class)
    val types: List<String>
) : Parcelable