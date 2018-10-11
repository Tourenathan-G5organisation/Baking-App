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
import com.tourenathan.bakingapp.bakingapp.adapter.RecipeIngredientDescriptionAdapter;
import com.tourenathan.bakingapp.bakingapp.model.Recipe;

import static com.tourenathan.bakingapp.bakingapp.adapter.RecipeIngredientDescriptionAdapter.RECIPE_DESCRIPTION_TYPE;
import static com.tourenathan.bakingapp.bakingapp.adapter.RecipeIngredientDescriptionAdapter.RECIPE_INGREDIENT_TYPE;

/**
 * A placeholder fragment containing a simple view.
 */
public class RecipeIngredientDescriptionActivityFragment extends Fragment implements OnDescriptionIngredientItemClickHandler {

    final static String TAG = RecipeIngredientDescriptionActivityFragment.class.getSimpleName();

    RecipeIngredientDescriptionAdapter mRecipeIngredientDescriptionAdapter;
    RecyclerView mRecipeDescriptionRecyclerview;
    Recipe recipe;

    public RecipeIngredientDescriptionActivityFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_ingredient_description, container, false);
        if (getActivity().getIntent()!=null && getActivity().getIntent().hasExtra(Intent.EXTRA_TEXT)) {
            Log.d(TAG,getActivity().getIntent().getStringExtra(Intent.EXTRA_TEXT));
            Gson gson = new Gson();
            recipe = gson.fromJson(getActivity().getIntent().getStringExtra(Intent.EXTRA_TEXT), Recipe.class);
            mRecipeIngredientDescriptionAdapter = new RecipeIngredientDescriptionAdapter(this);
            mRecipeDescriptionRecyclerview = rootView.findViewById(R.id.recipe_description_ingredient_recyclerview);
            mRecipeIngredientDescriptionAdapter.setData(recipe);
            mRecipeDescriptionRecyclerview.setAdapter(mRecipeIngredientDescriptionAdapter);
            getActivity().setTitle(String.format("%s %s", recipe.getName(), getString(R.string.title_activity_recipe_ingredient_description)));
        } else {
            getActivity().finish();
        }
        return rootView;
    }

    @Override
    public void onClick(String jsonData, int itemType) {
        if (itemType == RECIPE_INGREDIENT_TYPE){
            Intent intent = new Intent(getActivity(), RecipeIngredientActivity.class);
            intent.putExtra(Intent.EXTRA_TEXT, jsonData);
            intent.putExtra("name", recipe.getName());
            startActivity(intent);
        }
        else if (itemType == RECIPE_DESCRIPTION_TYPE){
            Intent intent = new Intent(getActivity(), RecipeStepActivity.class);
            intent.putExtra(Intent.EXTRA_TEXT, jsonData);
            intent.putExtra("name", recipe.getName());
            startActivity(intent);
        }
    }
}
