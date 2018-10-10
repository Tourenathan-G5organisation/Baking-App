package com.tourenathan.bakingapp.bakingapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tourenathan.bakingapp.bakingapp.adapter.RecipeIngredientAdapter;
import com.tourenathan.bakingapp.bakingapp.model.Ingredient;

import java.lang.reflect.Type;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class RecipeIngredientActivityFragment extends Fragment {

    RecyclerView mRecyclerview;
    RecipeIngredientAdapter mIngredientAdapter;

    public RecipeIngredientActivityFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_ingredient, container, false);
        if (getActivity().getIntent() != null && getActivity().getIntent().hasExtra(Intent.EXTRA_TEXT)) {
            Type ingredientType = new TypeToken<List<Ingredient>>() { }.getType();
            Gson gson = new Gson();
            List<Ingredient> ingredientList = gson.fromJson(getActivity().getIntent().getStringExtra(Intent.EXTRA_TEXT), ingredientType);
            mIngredientAdapter = new RecipeIngredientAdapter();
            mIngredientAdapter.setData(ingredientList);
            mRecyclerview = rootView.findViewById(R.id.ingredient_recyclerview);
            mRecyclerview.setAdapter(mIngredientAdapter);

            getActivity().setTitle(String.format("%s %s", getActivity().getIntent().getStringExtra("name"), getString(R.string.ingredient)));

        } else {
            getActivity().finish();
        }

        return rootView;

    }
}
