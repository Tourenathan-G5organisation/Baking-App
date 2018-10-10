package com.tourenathan.bakingapp.bakingapp;

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
import com.tourenathan.bakingapp.bakingapp.adapter.RecipientIngredientDescriptionAdapter;
import com.tourenathan.bakingapp.bakingapp.model.Recipe;

/**
 * A placeholder fragment containing a simple view.
 */
public class RecipeIngredientDescriptionActivityFragment extends Fragment {

    final static String TAG = RecipeIngredientDescriptionActivityFragment.class.getSimpleName();

    RecipientIngredientDescriptionAdapter mRecipientIngredientDescriptionAdapter;
    RecyclerView mRecipeDescriptionRecyclerview;

    public RecipeIngredientDescriptionActivityFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_ingredient_description, container, false);
        if (getActivity().getIntent()!=null && getActivity().getIntent().hasExtra(Intent.EXTRA_TEXT)) {
            Log.d(TAG,getActivity().getIntent().getStringExtra(Intent.EXTRA_TEXT));
            Gson gson = new Gson();
            Recipe recipe = gson.fromJson(getActivity().getIntent().getStringExtra(Intent.EXTRA_TEXT), Recipe.class);
            mRecipientIngredientDescriptionAdapter = new RecipientIngredientDescriptionAdapter();
            mRecipeDescriptionRecyclerview = rootView.findViewById(R.id.recipe_description_ingredient_recyclerview);
            mRecipientIngredientDescriptionAdapter.setData(recipe);
            mRecipeDescriptionRecyclerview.setAdapter(mRecipientIngredientDescriptionAdapter);
        } else {
            getActivity().finish();
        }
        return rootView;
    }
}
