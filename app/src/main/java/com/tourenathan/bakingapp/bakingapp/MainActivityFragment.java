package com.tourenathan.bakingapp.bakingapp;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tourenathan.bakingapp.bakingapp.adapter.RecipeAdapter;
import com.tourenathan.bakingapp.bakingapp.model.AppDatabase;
import com.tourenathan.bakingapp.bakingapp.model.AppExecutors;
import com.tourenathan.bakingapp.bakingapp.model.Ingredient;
import com.tourenathan.bakingapp.bakingapp.model.Recipe;
import com.tourenathan.bakingapp.bakingapp.rest.BakingApiClient;
import com.tourenathan.bakingapp.bakingapp.rest.BakingService;
import com.tourenathan.bakingapp.bakingapp.util.AppUtil;

import java.lang.reflect.Type;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements ItemOnClickHandler {

    public static final String TAG = MainActivityFragment.class.getSimpleName();

    List<Recipe> mRecipe;

    @BindView(R.id.recipe_recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
    @BindView(R.id.internet_error_message)
    LinearLayout mErrorMessageLayout;
    @BindView(R.id.try_again_button)
    Button mTryAgain;
    //LinearLayoutManager mLinearlayoutManager;
    RecipeAdapter mAdapter;
    Gson gson;

    // App Database reference
    AppDatabase mDb;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, rootView);
        gson = new Gson();
        mDb = AppDatabase.getsInstance(getActivity().getApplicationContext());

        if (savedInstanceState != null) {
            Type type = new TypeToken<List<Recipe>>() {
            }.getType();
            mRecipe = gson.fromJson(savedInstanceState.getString(Intent.EXTRA_TEXT), type);
            showContent();
        } else {
            LiveData<List<Recipe>> listLiveData = mDb.recipeDao().getAllRecipe();
            listLiveData.observe(this, new Observer<List<Recipe>>() {
                @Override
                public void onChanged(@Nullable List<Recipe> recipes) {
                    mRecipe = recipes;
                    if (recipes.size() > 0) {
                        showContent();
                        mAdapter.setData(mRecipe);
                    }

                }
            });

            getRecipeOnline();
        }

        mAdapter = new RecipeAdapter(this);
        mAdapter.setData(mRecipe);
        //mLinearlayoutManager = new LinearLayoutManager(getContext());
        //mRecyclerView.setLayoutManager(mLinearlayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoader();
                getRecipeOnline();
            }
        });
        return rootView;
    }

    /**
     * Get the list of recipe online
     */
    void getRecipeOnline() {
        if (AppUtil.isOnline(getContext())) {
            BakingService bakingService = BakingApiClient.createService(BakingService.class);
            final Call<List<Recipe>> recipeList = bakingService.getRecipe();

            recipeList.enqueue(new Callback<List<Recipe>>() {
                @Override
                public void onResponse(@NonNull Call<List<Recipe>> call, @NonNull Response<List<Recipe>> response) {
                    mRecipe = response.body();
                    mAdapter.setData(mRecipe);
                    Gson gson = new Gson();
                    String data = gson.toJson(mRecipe);
                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            mDb.recipeDao().insertAll(mRecipe);
                        }
                    });
                    Log.d(TAG, data);
                }

                @Override
                public void onFailure(@NonNull Call<List<Recipe>> call, @NonNull Throwable t) {
                    t.printStackTrace();
                }
            });
        } else if (mRecipe == null || mRecipe.size() == 0) {
            showErrorMessage();
        }
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

    void showLoader() {
        mErrorMessageLayout.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    void hideLoader() {
        mProgressBar.setVisibility(View.GONE);
        mErrorMessageLayout.setVisibility(View.GONE);
    }

    void showContent() {
        hideLoader();
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    void showErrorMessage() {
        mRecyclerView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);
        mErrorMessageLayout.setVisibility(View.VISIBLE);
    }
}
