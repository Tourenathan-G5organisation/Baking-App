package com.tourenathan.bakingapp.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tourenathan.bakingapp.bakingapp.model.Step;

import java.lang.reflect.Type;
import java.util.List;

import static com.tourenathan.bakingapp.bakingapp.RecipeStepActivity.RECIPE_NAME;
import static com.tourenathan.bakingapp.bakingapp.RecipeStepActivity.RECIPE_POSITION;
import static com.tourenathan.bakingapp.bakingapp.adapter.RecipeIngredientDescriptionAdapter.RECIPE_DESCRIPTION_TYPE;
import static com.tourenathan.bakingapp.bakingapp.adapter.RecipeIngredientDescriptionAdapter.RECIPE_INGREDIENT_TYPE;

public class RecipeIngredientDescriptionActivity extends AppCompatActivity implements OnDescriptionIngredientItemClickHandler {

    boolean mTwoPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_ingredient_description);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FrameLayout recipeDetailsFrameLayout = findViewById(R.id.recipe_detail_fragment);
        if (recipeDetailsFrameLayout != null) {
            mTwoPane = true;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return (super.onOptionsItemSelected(item));
    }

    @Override
    public void onClick(String jsonData, String recipeName, int itemPosition, int itemType) {
        if (mTwoPane) {
            FragmentManager fragmentManager = getSupportFragmentManager();

            if (itemType == RECIPE_INGREDIENT_TYPE) {
                RecipeIngredientActivityFragment fragment =
                        new RecipeIngredientActivityFragment();
                fragment.setData(jsonData);
                fragmentManager.beginTransaction()
                        .replace(R.id.recipe_detail_fragment, fragment)
                        .commit();

            } else if (itemType == RECIPE_DESCRIPTION_TYPE) {
                Type stepListType = new TypeToken<List<Step>>() {
                }.getType();
                RecipeStepActivityFragment fragment = new RecipeStepActivityFragment();
                Gson gson = new Gson();
                List<Step> mStep = gson.fromJson(jsonData, stepListType);
                fragment.setStep(mStep.get(itemPosition));

                fragmentManager.beginTransaction()
                        .replace(R.id.recipe_detail_fragment, fragment)
                        .commit();
            }
        } else {
            if (itemType == RECIPE_INGREDIENT_TYPE) {
                Intent intent = new Intent(this, RecipeIngredientActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, jsonData);
                intent.putExtra("name", recipeName);
                startActivity(intent);
            } else if (itemType == RECIPE_DESCRIPTION_TYPE) {
                Intent intent = new Intent(this, RecipeStepActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, jsonData);
                intent.putExtra(RECIPE_NAME, recipeName);
                intent.putExtra(RECIPE_POSITION, itemPosition);
                startActivity(intent);
            }
        }

    }

}
