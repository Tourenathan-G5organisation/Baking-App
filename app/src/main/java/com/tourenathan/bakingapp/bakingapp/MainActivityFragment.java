package com.tourenathan.bakingapp.bakingapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tourenathan.bakingapp.bakingapp.adapter.RecipeAdapter;
import com.tourenathan.bakingapp.bakingapp.model.Ingredient;
import com.tourenathan.bakingapp.bakingapp.model.Recipe;
import com.tourenathan.bakingapp.bakingapp.rest.BakingApiClient;
import com.tourenathan.bakingapp.bakingapp.rest.BakingService;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements ItemOnClickHandler {

    public static final String TAG = MainActivityFragment.class.getSimpleName();

    List<Recipe> mRecipe;
    RecyclerView mRecyclerView;
    //LinearLayoutManager mLinearlayoutManager;
    RecipeAdapter mAdapter;
    Gson gson;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        gson = new Gson();
        if (savedInstanceState != null) {
            Type type = new TypeToken<List<Recipe>>() { }.getType();
            mRecipe = gson.fromJson(savedInstanceState.getString(Intent.EXTRA_TEXT), type);
        } else {
            getRecipeOnline();
        }

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mRecyclerView = rootView.findViewById(R.id.recipe_recyclerview);
        mAdapter = new RecipeAdapter(this);
        mAdapter.setData(mRecipe);
        //mLinearlayoutManager = new LinearLayoutManager(getContext());
        //mRecyclerView.setLayoutManager(mLinearlayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }

    /**
     * Get the list of recipe online
     */
    void getRecipeOnline() {
        BakingService bakingService = BakingApiClient.createService(BakingService.class);
        Call<List<Recipe>> recipeList = bakingService.getRecipe();

        recipeList.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(@NonNull Call<List<Recipe>> call, @NonNull Response<List<Recipe>> response) {
                mRecipe = response.body();
                mAdapter.setData(mRecipe);
                Gson gson = new Gson();
                String data = gson.toJson(mRecipe);
                Log.d(TAG, data);
            }

            @Override
            public void onFailure(@NonNull Call<List<Recipe>> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public void onClick(Recipe recipe) {
        Intent intent = new Intent(getContext(), RecipeIngredientDescriptionActivity.class);
        Gson gson = new Gson();
        intent.putExtra(Intent.EXTRA_TEXT, gson.toJson(recipe));
        startActivity(intent);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(Intent.EXTRA_TEXT, gson.toJson(mRecipe));
        super.onSaveInstanceState(outState);
    }
}
