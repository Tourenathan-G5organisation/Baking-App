package com.tourenathan.bakingapp.bakingapp.model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.util.Log;

@Database(entities = {Recipe.class}, version = 1, exportSchema = false)
@TypeConverters({IngredientConverter.class, StepConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    private static final Object LOCK = new Object();
    private static final String LOG_TAC = AppDatabase.class.getSimpleName();
    private static final String DATABASE_NAME = "recipe_db";

    private static AppDatabase sInstance;

    public static AppDatabase getsInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAC, "Creating new database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                        .build();
            }
        }
        Log.d(LOG_TAC, "Getting the database instance");
        return sInstance;
    }

    public abstract RecipeDao recipeDao();
}
