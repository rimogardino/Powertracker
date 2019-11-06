package com.example.powertracker.room

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import java.text.SimpleDateFormat
import java.util.*


@Database(entities = [ExerciseEntity::class], version = 3)
@TypeConverters(MapConverter::class)
abstract class ExerciseDatabase : RoomDatabase() {

    abstract fun exerciseDao() : ExerciseDao

    companion object {
        @Volatile
        private var INSTACE: ExerciseDatabase? = null

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Change foodTime, timeOfDay and coffee to Strings from int index of the list below
                /*
                old list values were
                private val foodTimes = listOf("Right now","30 minutes","1 hour","2 hours +")
                private val coffee_Q = listOf("Yes","No","Predjasah","Tea")
                private val timeOfDay = listOf("Morning","Noon","Evening")

                 */

                //Create buffer/new table
                database.execSQL("CREATE TABLE ExerciseEntity_new (date TEXT NOT NULL, " +
                        "exercise_1 TEXT, exercise_2 TEXT, exercise_3 TEXT, exercise_4 TEXT," +
                        " exercise_5 TEXT, exercise_6 TEXT, exercise_7 TEXT," +
                        "sleep INTEGER NOT NULL, foodTime TEXT NOT NULL, timeOfDay TEXT NOT NULL, " +
                        "restDays INTEGER NOT NULL," +
                        "coffee TEXT NOT NULL, extras TEXT NOT NULL, howFeel REAL NOT NULL, " +
                        "PRIMARY KEY('date'))")
                // Copy the data
                database.execSQL("INSERT INTO ExerciseEntity_new " +
                        "(date, exercise_1, exercise_2, exercise_3, exercise_4, exercise_5, " +
                        "exercise_6, exercise_7, sleep, foodTime, timeOfDay, restDays, coffee, " +
                        "howFeel, extras) " +
                        "SELECT date, exercise_1, exercise_2, exercise_3, exercise_4, exercise_5, " +
                        "exercise_6, exercise_7, sleep, foodTime, timeOfDay, restDays, coffee, " +
                        "howFeel, extras FROM ExerciseEntity")
                // Remove the old table
                database.execSQL("DROP TABLE ExerciseEntity")
                // Change the table name to the correct one
                database.execSQL("ALTER TABLE ExerciseEntity_new RENAME TO ExerciseEntity")
                }


            }

        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Change sleep from int to float

                //Create buffer/new table
                database.execSQL("CREATE TABLE ExerciseEntity_new (date TEXT NOT NULL, " +
                        "exercise_1 TEXT, exercise_2 TEXT, exercise_3 TEXT, exercise_4 TEXT," +
                        " exercise_5 TEXT, exercise_6 TEXT, exercise_7 TEXT," +
                        "sleep REAL NOT NULL, foodTime TEXT NOT NULL, timeOfDay TEXT NOT NULL, " +
                        "restDays INTEGER NOT NULL," +
                        "coffee TEXT NOT NULL, extras TEXT NOT NULL, howFeel REAL NOT NULL, " +
                        "PRIMARY KEY('date'))")
                // Copy the data
                database.execSQL("INSERT INTO ExerciseEntity_new " +
                        "(date, exercise_1, exercise_2, exercise_3, exercise_4, exercise_5, " +
                        "exercise_6, exercise_7, sleep, foodTime, timeOfDay, restDays, coffee, " +
                        "howFeel, extras) " +
                        "SELECT date, exercise_1, exercise_2, exercise_3, exercise_4, exercise_5, " +
                        "exercise_6, exercise_7, sleep, foodTime, timeOfDay, restDays, coffee, " +
                        "howFeel, extras FROM ExerciseEntity")
                // Remove the old table
                database.execSQL("DROP TABLE ExerciseEntity")
                // Change the table name to the correct one
                database.execSQL("ALTER TABLE ExerciseEntity_new RENAME TO ExerciseEntity")
            }


        }





        fun getDatabase(context: Context) : ExerciseDatabase {
            val tempInstance = INSTACE
            if (tempInstance != null) {
                return tempInstance
            } else {
                synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        ExerciseDatabase::class.java,
                        "ExerciseDatabase")
                        //.addCallback(InitCallback())
                        .addMigrations(MIGRATION_1_2,MIGRATION_2_3)
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTACE = instance
                    Log.d("${ExerciseDatabase::class}", "Creating the database")
                    return instance
                }
            }
        }



    }



}