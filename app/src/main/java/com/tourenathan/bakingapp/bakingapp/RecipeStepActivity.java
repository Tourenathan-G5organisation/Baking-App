package com.tourenathan.bakingapp.bakingapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tourenathan.bakingapp.bakingapp.model.Ingredient;
import com.tourenathan.bakingapp.bakingapp.model.Recipe;
import com.tourenathan.bakingapp.bakingapp.model.Step;

import java.lang.reflect.Type;
import java.util.List;

import static android.view.View.SYSTEM_UI_FLAG_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE;

public class RecipeStepActivity extends AppCompatActivity {

    public static final String RECIPE_NAME = "name";
    public static final String RECIPE_POSITION = "position";

    List<Step> mStep;
    RecipeStepActivityFragment mFragment;
    int mPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step);
        if (!getResources().getBoolean(R.bool.is_landscape)) {
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } else {
            if (getSupportActionBar() != null) {
                getSupportActionBar().hide();
            }
            hideSystemUI();

        }
        if (savedInstanceState != null) {
            mPosition = savedInstanceState.getInt(RECIPE_POSITION);
        }

        if (getIntent() != null && getIntent().hasExtra(RECIPE_NAME)) {
            setTitle(String.format("%s %s", getIntent().getStringExtra(RECIPE_NAME), getString(R.string.step)));
            if (getIntent().hasExtra(Intent.EXTRA_TEXT)) {
                Gson gson = new Gson();
                if (mPosition == -1)
                    mPosition = getIntent().getIntExtra(RECIPE_POSITION, 0);
                Type stepListType = new TypeToken<List<Step>>() {
                }.getType();
                mStep = gson.fromJson(getIntent().getStringExtra(Intent.EXTRA_TEXT), stepListType);
                mFragment = (RecipeStepActivityFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentStep);
                mFragment.setStep(mStep.get(mPosition));
                mFragment.initialiseData();
            }
        }
        Button next = findViewById(R.id.next_step_button);
        Button prev = findViewById(R.id.prev_step_button);

        if (prev != null) {
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mPosition < mStep.size() - 1) {
                        mFragment.setStep(mStep.get(mPosition += 1));
                        mFragment.initialiseData();
                        mFragment.releasePlayer();
                        mFragment.initPlayer();
                    }
                }
            });

            prev.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mPosition > 0) {
                        mFragment.setStep(mStep.get(mPosition -= 1));
                        mFragment.initialiseData();
                        mFragment.releasePlayer();
                        mFragment.initPlayer();
                    }
                }

            });
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

    private void hideSystemUI() {
        AppBarLayout appBarLayout = findViewById(R.id.app_bar);
        appBarLayout.setVisibility(View.GONE);
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                View.SYSTEM_UI_FLAG_IMMERSIVE |
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    // Shows the system bars by removing all the flags
// except for the ones that make the content appear under the system bars.
    private void showSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(RECIPE_POSITION, mPosition);
        super.onSaveInstanceState(outState);
    }
}
