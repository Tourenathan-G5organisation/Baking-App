package com.tourenathan.bakingapp.bakingapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import com.tourenathan.bakingapp.bakingapp.model.Ingredient;

import java.util.List;

public class RecipeIngredientWidgetAdapter extends ArrayAdapter<Ingredient> {
    public RecipeIngredientWidgetAdapter(@NonNull Context context, int resource, @NonNull List<Ingredient> objects) {
        super(context, resource, objects);
    }
}
