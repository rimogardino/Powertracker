package com.example.powertracker.room

import android.app.Application
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class ExerciseRepository(application: Application) {
    private val exerciseDatabase = ExerciseDatabase.getDatabase(application)
    private val exerciseDao = exerciseDatabase.exerciseDao()
    val allSeshs = exerciseDao.getAll()

    //unclear if returning everything does anything
    @WorkerThread
    fun insert(exerciseEntity: ExerciseEntity) {
        exerciseDao.insert(exerciseEntity)
    }

    @WorkerThread
    fun delete(exerciseEntity: ExerciseEntity) {
        exerciseDao.delete(exerciseEntity)
    }

    @WorkerThread
    fun update(exerciseEntity: ExerciseEntity) {
        exerciseDao.update(exerciseEntity)
    }

    @WorkerThread
    fun updateFeatures(exerciseEntity: ExerciseEntity) {
        exerciseDao.updateFeatures(exerciseEntity.date,exerciseEntity.sleep,exerciseEntity.foodTime,
            exerciseEntity.timeOfDay,exerciseEntity.restDays,exerciseEntity.coffee,
            exerciseEntity.howFeel, exerciseEntity.extras)
    }

    @WorkerThread
    fun updateExercises(exerciseEntity: ExerciseEntity) {
        exerciseDao.updateExercises(exerciseEntity.date,exerciseEntity.exercise_1,exerciseEntity.exercise_2,
            exerciseEntity.exercise_3,exerciseEntity.exercise_4,exerciseEntity.exercise_5,
            exerciseEntity.exercise_6,exerciseEntity.exercise_7)
    }

    @WorkerThread
    fun getDate(date: String) : LiveData<ExerciseEntity> {
        return exerciseDao.getDate(date)
    }

    @WorkerThread
    fun getAll() : LiveData<List<ExerciseEntity>> {
        return exerciseDao.getAll()
    }


}