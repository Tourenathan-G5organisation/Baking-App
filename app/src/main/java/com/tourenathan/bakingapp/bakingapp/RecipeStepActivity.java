package com.tourenathan.bakingapp.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tourenathan.bakingapp.bakingapp.model.Ingredient;
import com.tourenathan.bakingapp.bakingapp.model.Recipe;
import com.tourenathan.bakingapp.bakingapp.model.Step;

import java.lang.reflect.Type;
import java.util.List;

public class RecipeStepActivity extends AppCompatActivity {

    public static final String RECIPE_NAME = "name";
    public static final String RECIPE_POSITION = "position";

    List<Step> mStep;
    RecipeStepActivityFragment mFragment;
    int mPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getIntent() != null && getIntent().hasExtra(RECIPE_NAME)) {
            setTitle(String.format("%s %s", getIntent().getStringExtra(RECIPE_NAME), getString(R.string.step)));
            if (getIntent().hasExtra(Intent.EXTRA_TEXT)) {
                Gson gson = new Gson();
                mPosition = getIntent().getIntExtra(RECIPE_POSITION, 0);
                Type steplistType = new TypeToken<List<Step>>() {
                }.getType();
                mStep = gson.fromJson(getIntent().getStringExtra(Intent.EXTRA_TEXT), steplistType);
                mFragment = (RecipeStepActivityFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentStep);
                mFragment.setStep(mStep.get(mPosition));
                mFragment.initialiseData();
            }
        }
        Button next = findViewById(R.id.next_step_button);
        Button prev = findViewById(R.id.prev_step_button);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPosition < mStep.size()-1) {
                    mFragment.setStep(mStep.get(mPosition += 1));
                    mFragment.initialiseData();
                }
            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPosition > 0) {
                    mFragment.setStep(mStep.get(mPosition -= 1));
                    mFragment.initialiseData();
                }
            }

        });

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

}
