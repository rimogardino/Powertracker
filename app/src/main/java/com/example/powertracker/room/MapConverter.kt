package com.example.powertracker.room

import androidx.room.TypeConverter

class MapConverter {

    @TypeConverter
    fun mapToString(mapche: List<Any>?) : String? {
        return mapche.toString()
    }

    @TypeConverter
    fun stringToMap(stringMap: String): List<Any>? {
        if (stringMap.compareTo("null") == 0) return null

        return stringMap.substring(1, stringMap.length - 1).split(", ")
    }
}