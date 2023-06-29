package com.example.roomapp.converter

import android.text.TextUtils
import androidx.room.TypeConverter
import com.example.roomapp.data.Exclusions
import com.example.roomapp.data.Facilities
import com.example.roomapp.data.Options
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class Converter {
    @TypeConverter
    fun stringToFacilities(string: String?): Facilities? {
        if (TextUtils.isEmpty(string))
            return null
        return Gson().fromJson(string, Facilities::class.java)
    }

    @TypeConverter
    fun facilitiesToString(facilities: Facilities?): String? {
        return Gson().toJson(facilities)
    }

    @TypeConverter
    fun stringToOptions(string: String?): Options? {
        if (TextUtils.isEmpty(string))
            return null
        return Gson().fromJson(string, Options::class.java)
    }

    @TypeConverter
    fun optionsToString(options: Options?): String? {
        return Gson().toJson(options)
    }

    @TypeConverter
    fun stringToFacilitiesList(data: String): List<Facilities> {
        val gson = Gson()
        val listType: Type = object : TypeToken<List<Facilities>>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun facilitiesListToString(myObjects: List<Facilities>): String {
        val gson = Gson()
        return gson.toJson(myObjects)
    }

    @TypeConverter
    fun stringToExclusionsList(data: String): List<Exclusions> {
        val gson = Gson()
        val listType: Type = object : TypeToken<List<Exclusions>>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun exclusionsListToString(myObjects: List<Exclusions>): String {
        val gson = Gson()
        return gson.toJson(myObjects)
    }

    @TypeConverter
    fun stringToExclusionsList1(data: String): List<List<Exclusions>> {
        val gson = Gson()
        val listType: Type = object : TypeToken<List<List<Exclusions>>>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun exclusionsListToString1(myObjects: List<List<Exclusions>>): String {
        val gson = Gson()
        return gson.toJson(myObjects)
    }
}