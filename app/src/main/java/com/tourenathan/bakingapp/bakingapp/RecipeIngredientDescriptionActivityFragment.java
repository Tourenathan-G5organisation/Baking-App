package com.tourenathan.bakingapp.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tourenathan.bakingapp.bakingapp.adapter.RecipeIngredientDescriptionAdapter;
import com.tourenathan.bakingapp.bakingapp.model.Recipe;

import java.lang.reflect.Type;
import java.util.List;

import static com.tourenathan.bakingapp.bakingapp.adapter.RecipeIngredientDescriptionAdapter.RECIPE_DESCRIPTION_TYPE;
import static com.tourenathan.bakingapp.bakingapp.adapter.RecipeIngredientDescriptionAdapter.RECIPE_INGREDIENT_TYPE;

/**
 * A placeholder fragment containing a simple view.
 */
public class RecipeIngredientDescriptionActivityFragment extends Fragment {

    final static String TAG = RecipeIngredientDescriptionActivityFragment.class.getSimpleName();

    RecipeIngredientDescriptionAdapter mRecipeIngredientDescriptionAdapter;
    RecyclerView mRecipeDescriptionRecyclerview;
    Recipe recipe;
    OnDescriptionIngredientItemClickHandler mOnClickHandler;

    public RecipeIngredientDescriptionActivityFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mOnClickHandler = (OnDescriptionIngredientItemClickHandler) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnDescriptionIngredientItemClickHandler");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_ingredient_description, container, false);
        Gson gson = new Gson();
        if (savedInstanceState != null) {
            recipe = gson.fromJson(savedInstanceState.getString(Intent.EXTRA_TEXT), Recipe.class);
        } else if (getActivity().getIntent() != null && getActivity().getIntent().hasExtra(Intent.EXTRA_TEXT)) {
            Log.d(TAG, getActivity().getIntent().getStringExtra(Intent.EXTRA_TEXT));
            recipe = gson.fromJson(getActivity().getIntent().getStringExtra(Intent.EXTRA_TEXT), Recipe.class);
        }

        mRecipeIngredientDescriptionAdapter = new RecipeIngredientDescriptionAdapter(mOnClickHandler);
        mRecipeDescriptionRecyclerview = rootView.findViewById(R.id.recipe_description_ingredient_recyclerview);
        mRecipeIngredientDescriptionAdapter.setData(recipe);
        mRecipeDescriptionRecyclerview.setAdapter(mRecipeIngredientDescriptionAdapter);
        getActivity().setTitle(String.format("%s %s", recipe.getName(), getString(R.string.title_activity_recipe_ingredient_description)));

        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        Gson gson = new Gson();
        outState.putString(Intent.EXTRA_TEXT, gson.toJson(recipe));
        super.onSaveInstanceState(outState);
    }

}
