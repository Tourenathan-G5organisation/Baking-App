package com.tourenathan.bakingapp.bakingapp.model;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface RecipeDao {

    @Query("SELECT * FROM recipe")
    LiveData<List<Recipe>> getAllRecipe();

    @Query("SELECT * FROM recipe WHERE id = :id")
    LiveData<Recipe> getRecipeById(int id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<Recipe> items);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Recipe item);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Recipe item);

    @Delete
    void delete(Recipe item);

    @Query("DELETE  FROM recipe")
    void deleteAllMovies();
}
