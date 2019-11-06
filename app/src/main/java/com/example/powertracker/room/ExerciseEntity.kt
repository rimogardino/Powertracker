package com.example.powertracker.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ExerciseEntity(
    @PrimaryKey var date: String,
    var exercise_1: List<Any>?,
    var exercise_2: List<Any>?,
    var exercise_3: List<Any>?,
    var exercise_4: List<Any>?,
    var exercise_5: List<Any>?,
    var exercise_6: List<Any>?,
    var exercise_7: List<Any>?,
    var sleep: Float,
    var foodTime: String,
    var timeOfDay: String,
    var restDays: Int,
    var coffee: String,
    var howFeel: Float,
    var extras: String
)