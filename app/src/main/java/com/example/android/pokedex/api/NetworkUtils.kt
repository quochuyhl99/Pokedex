package com.example.android.pokedex.api

import com.example.android.pokedex.model.Pokemon
import org.json.JSONObject

fun parsePokemonJsonResult(jsonResult: JSONObject): Pokemon {
    val id = jsonResult.getLong("id")
    val baseExperience = jsonResult.getInt("base_experience")
    val height = jsonResult.getInt("height")
    val name = jsonResult.getString("name")
    val weight = jsonResult.getInt("weight")
    val frontDefault = jsonResult.getJSONObject("sprites").getJSONObject("other").getJSONObject("official-artwork")
        .getString("front_default")
    val frontShiny = jsonResult.getJSONObject("sprites").getJSONObject("other").getJSONObject("official-artwork")
        .getString("front_shiny")

    val statsJsonArray = jsonResult.getJSONArray("stats")
    val hp = statsJsonArray.getJSONObject(0).getInt("base_stat")
    val attack = statsJsonArray.getJSONObject(1).getInt("base_stat")
    val defense = statsJsonArray.getJSONObject(2).getInt("base_stat")
    val specialAttack = statsJsonArray.getJSONObject(3).getInt("base_stat")
    val specialDefence = statsJsonArray.getJSONObject(4).getInt("base_stat")
    val speed = statsJsonArray.getJSONObject(5).getInt("base_stat")
    val typesJsonArray = jsonResult.getJSONArray("types")
    val types = mutableListOf<String>()
    for (i in 0 until typesJsonArray.length()) {
        val typeJson = typesJsonArray.getJSONObject(i)
        val typeDetailsJson = typeJson.getJSONObject("type")
        val typeName = typeDetailsJson.getString("name")
        types.add(typeName)
    }

    return Pokemon(
        id,
        baseExperience,
        height,
        name,
        weight,
        frontDefault,
        frontShiny,
        hp,
        attack,
        defense,
        specialAttack,
        specialDefence,
        speed,
        types
    )
}