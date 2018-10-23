package com.tourenathan.bakingapp.bakingapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
    List<Ingredient> mIngredientList;
    Gson gson = new Gson();

    public RecipeIngredientActivityFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_ingredient, container, false);
        Type ingredientType = new TypeToken<List<Ingredient>>() {
        }.getType();

        if (savedInstanceState != null) {
            mIngredientList = gson.fromJson(savedInstanceState.getString(Intent.EXTRA_TEXT), ingredientType);

        } else if (getActivity().getIntent() != null &&
                       (mIngredientList == null) &&
                            getActivity().getIntent().hasExtra(Intent.EXTRA_TEXT)) {

            mIngredientList = gson.fromJson(getActivity().getIntent().getStringExtra(Intent.EXTRA_TEXT), ingredientType);


        }
        mIngredientAdapter = new RecipeIngredientAdapter();
        mIngredientAdapter.setData(mIngredientList);
        mRecyclerview = rootView.findViewById(R.id.ingredient_recyclerview);
        mRecyclerview.setAdapter(mIngredientAdapter);/*else {
            getActivity().finish();
        }*/

        return rootView;

    }

    public void setData(String text) {
        Type ingredientType = new TypeToken<List<Ingredient>>() {
        }.getType();
        gson = new Gson();
        mIngredientList = gson.fromJson(text, ingredientType);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(Intent.EXTRA_TEXT, gson.toJson(mIngredientList));
        super.onSaveInstanceState(outState);
    }
}
