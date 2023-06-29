package com.example.roomapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Data(@PrimaryKey(autoGenerate = true) val Id:Int, val facilities: List<Facilities>, val exclusions: List<List<Exclusions>>)
data class Facilities(val facility_id: String, val name: String, val options: List<Options>)
data class Options(val name: String, val icon: String, val id: String)
data class Exclusions(val facility_id: String, val options_id: String)
