package com.example.powertracker.room

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao

@Dao
interface ExerciseDao {

    @Insert
    fun insert(exercise: ExerciseEntity)

    @Delete
    fun delete(exercise: ExerciseEntity)

    @Update
    fun update(exercise: ExerciseEntity)



    @Query("UPDATE ExerciseEntity SET sleep = :sleep, foodTime = :foodTime, " +
            "timeOfday = :timeOfDay, restDays = :restDays, coffee = :coffee, extras = :extras," +
            "howFeel = :howFeel WHERE date == :date")
    fun updateFeatures(date: String,sleep: Float = -666f, foodTime: String = "-666",
                       timeOfDay: String = "-666", restDays: Int = -666, coffee: String = "-666",
                       howFeel: Float = -666f, extras: String = "")

    @Query("UPDATE ExerciseEntity SET exercise_1 = :exercise_1, exercise_2 = :exercise_2, " +
            "exercise_3 = :exercise_3, exercise_4 = :exercise_4, exercise_5 = :exercise_5, " +
            "exercise_6 = :exercise_6, exercise_7 = :exercise_7 WHERE date == :date")
    fun updateExercises(date: String,exercise_1: List<Any>? = null,
                        exercise_2: List<Any>? = null,
                        exercise_3: List<Any>? = null,
                        exercise_4: List<Any>? = null,
                        exercise_5: List<Any>? = null,
                        exercise_6: List<Any>? = null,
                        exercise_7: List<Any>? = null)




    @Query("SELECT * FROM ExerciseEntity WHERE date == :date")
    fun getDate(date: String) : LiveData<ExerciseEntity>

    @Query("SELECT * FROM ExerciseEntity ORDER BY date DESC")
    fun getAll() : LiveData<List<ExerciseEntity>>

}