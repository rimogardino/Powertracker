package com.example.powertracker

import android.system.Os.close
import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry

import com.example.powertracker.room.ExerciseDatabase

import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class MigrationTest {
    private val TEST_DB = "migration-test"

    @get:Rule
    val helper: MigrationTestHelper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        ExerciseDatabase::class.java.canonicalName,
        FrameworkSQLiteOpenHelperFactory()
    )

    @Test
    @Throws(IOException::class)
    fun migrate1To2() {
        var db = helper.createDatabase(TEST_DB, 1).apply {
            // db has schema version 1. insert some data using SQL queries.
            // You cannot use DAO classes because they expect the latest schema.
            execSQL("INSERT INTO ExerciseEntity_new " +
                    "(date, exercise_1, exercise_2, exercise_3, exercise_4, exercise_5, " +
                    "exercise_6, exercise_7, sleep, foodTime, timeOfDay, restDays, coffee, " +
                    "howFeel, extras) " +
                    "VALUES ('25-10-2019', '{}', '{}', '{}', '{}', '{}', " +
                    "'{}', '{}', 1, 1, 1, 1, 1, " +
                    "0.2, 'data base test')")

            // Prepare for the next version.
            close()
        }

        // Re-open the database with version 2 and provide
        // MIGRATION_1_2 as the migration process.
        db = helper.runMigrationsAndValidate(TEST_DB, 2, true, ExerciseDatabase.MIGRATION_1_2)

        // MigrationTestHelper automatically verifies the schema changes,
        // but you need to validate that the data was migrated properly.


    }
}
