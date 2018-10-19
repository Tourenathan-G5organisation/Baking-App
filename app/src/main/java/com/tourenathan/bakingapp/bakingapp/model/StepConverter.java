package com.tourenathan.bakingapp.bakingapp.model;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class StepConverter {

    @TypeConverter
    public static List<Step> toList(String string) {
        if (string != null) {
            Gson gson = new Gson();
            Type stepListType = new TypeToken<List<Step>>() { }.getType();
            return gson.fromJson(string, stepListType);
        }
        return null;
    }

    @TypeConverter
    public static String toString(List<Step> stepsList) {
        Gson gson = new Gson();
        return stepsList == null ? null : gson.toJson(stepsList);
    }
}
