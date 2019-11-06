package com.example.powertracker.room

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ExerciseViewModel(application: Application) : AndroidViewModel(application) {
    private val exerciseRepository: ExerciseRepository = ExerciseRepository(application)
    val exerciseLiveData: LiveData<List<ExerciseEntity>>

    init {
        exerciseLiveData = exerciseRepository.allSeshs
    }


    fun insert(exerciseEntity: ExerciseEntity) {
        viewModelScope.launch(Dispatchers.IO) { exerciseRepository.insert(exerciseEntity) }
    }


    fun delete(exerciseEntity: ExerciseEntity) {
        viewModelScope.launch(Dispatchers.IO) {exerciseRepository.delete(exerciseEntity)}
    }


    fun update(exerciseEntity: ExerciseEntity) {
        //Log.d("Features","updating in view model ${exerciseEntity}")
        viewModelScope.launch(Dispatchers.IO) {exerciseRepository.update(exerciseEntity)}
    }

    fun updateFeatures(exerciseEntity: ExerciseEntity) {
        //Log.d("Features","updating in view model ${exerciseEntity}")
        viewModelScope.launch(Dispatchers.IO) {exerciseRepository.updateFeatures(exerciseEntity)}
    }

    fun updateExercises(exerciseEntity: ExerciseEntity) {
        //Log.d("Features","updating in view model ${exerciseEntity}")
        viewModelScope.launch(Dispatchers.IO) {exerciseRepository.updateExercises(exerciseEntity)}
    }

    fun getDate(date: String): LiveData<ExerciseEntity>? {
        var newDate: LiveData<ExerciseEntity>? = exerciseRepository.getDate(date)
        //viewModelScope.launch(Dispatchers.IO) {newDate = exerciseRepository.getDate(date)}
        Log.d("Features","getting date in view model $newDate")
        return newDate
    }


    fun getAll(): LiveData<List<ExerciseEntity>> {
        return exerciseLiveData
    }


}