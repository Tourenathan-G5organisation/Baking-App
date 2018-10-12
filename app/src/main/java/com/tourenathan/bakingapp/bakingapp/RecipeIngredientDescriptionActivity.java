package com.tourenathan.bakingapp.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import static com.tourenathan.bakingapp.bakingapp.RecipeStepActivity.RECIPE_NAME;
import static com.tourenathan.bakingapp.bakingapp.RecipeStepActivity.RECIPE_POSITION;
import static com.tourenathan.bakingapp.bakingapp.adapter.RecipeIngredientDescriptionAdapter.RECIPE_DESCRIPTION_TYPE;
import static com.tourenathan.bakingapp.bakingapp.adapter.RecipeIngredientDescriptionAdapter.RECIPE_INGREDIENT_TYPE;

public class RecipeIngredientDescriptionActivity extends AppCompatActivity implements OnDescriptionIngredientItemClickHandler {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_ingredient_description);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
        if (itemType == RECIPE_INGREDIENT_TYPE){
            Intent intent = new Intent(this, RecipeIngredientActivity.class);
            intent.putExtra(Intent.EXTRA_TEXT, jsonData);
            intent.putExtra("name", recipeName);
            startActivity(intent);
        }
        else if (itemType == RECIPE_DESCRIPTION_TYPE){
            Intent intent = new Intent(this, RecipeStepActivity.class);
            intent.putExtra(Intent.EXTRA_TEXT, jsonData);
            intent.putExtra(RECIPE_NAME, recipeName);
            intent.putExtra(RECIPE_POSITION, itemPosition);
            startActivity(intent);
        }
    }

}
