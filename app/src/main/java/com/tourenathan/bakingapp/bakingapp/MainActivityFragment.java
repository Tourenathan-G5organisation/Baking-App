package com.tourenathan.bakingapp.bakingapp;

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
import com.tourenathan.bakingapp.bakingapp.adapter.RecipeAdapter;
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

    List<Recipe> mRecipe;
    RecyclerView mRecyclerView;
    LinearLayoutManager mLinearlayoutManager;
    RecipeAdapter mAdapter;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getRecipeOnline();
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mRecyclerView = rootView.findViewById(R.id.recipe_recyclerview);
        mAdapter = new RecipeAdapter();
        mLinearlayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLinearlayoutManager);
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
}
