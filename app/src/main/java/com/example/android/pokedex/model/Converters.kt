package com.example.android.pokedex.model

import androidx.room.TypeConverter

class TypesConverter {
    @TypeConverter
    fun fromTypes(types: List<String>): String {
        return types.joinToString(separator = ",")
    }

    @TypeConverter
    fun toTypes(typesString: String): List<String> {
        return typesString.split(",")
    }
}