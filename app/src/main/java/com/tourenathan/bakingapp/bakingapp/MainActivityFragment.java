package com.tourenathan.bakingapp.bakingapp;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.tourenathan.bakingapp.bakingapp.model.Recipe;
import com.tourenathan.bakingapp.bakingapp.rest.BakingApiClient;
import com.tourenathan.bakingapp.bakingapp.rest.BakingService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public static final String TAG = MainActivityFragment.class.getSimpleName();

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getRecipeOnline();
        return inflater.inflate(R.layout.fragment_main, container, false);
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
                List<Recipe> recipe = response.body();
                Gson gson = new Gson();
                String data = gson.toJson(recipe);
                Log.d(TAG, data);
            }

            @Override
            public void onFailure(@NonNull Call<List<Recipe>> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
