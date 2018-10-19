package com.tourenathan.bakingapp.bakingapp.model;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class IngredientConverter {

    @TypeConverter
    public static List<Ingredient> toList(String string) {
        if (string != null) {
            Gson gson = new Gson();
            Type ingredientListType = new TypeToken<List<Ingredient>>() { }.getType();
            return gson.fromJson(string, ingredientListType);
        }
        return null;
    }

    @TypeConverter
    public static String toString(List<Ingredient> ingredientList) {
        Gson gson = new Gson();
        return ingredientList == null ? null : gson.toJson(ingredientList);
    }
}
