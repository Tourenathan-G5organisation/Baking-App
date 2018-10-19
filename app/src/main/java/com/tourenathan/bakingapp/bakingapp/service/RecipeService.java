package com.tourenathan.bakingapp.bakingapp.service;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.ComponentName;
import android.content.ContentProvider;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.tourenathan.bakingapp.bakingapp.RecipeIngredientWidget;
import com.tourenathan.bakingapp.bakingapp.model.AppDatabase;
import com.tourenathan.bakingapp.bakingapp.model.Recipe;

import java.util.List;

public class RecipeService extends IntentService {

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public RecipeService() {
        super("RecipeService");
    }

    public static void startUpdateWidget(Context context) {
        Intent intent = new Intent(context, RecipeService.class);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            // Trigger the display of updates on widget
            handleWidgetUpdate(this);
        }

    }

    private void handleWidgetUpdate(final Context context) {
        AppDatabase mDb = AppDatabase.getsInstance(getApplicationContext());
        final List<Recipe> recipeData = mDb.recipeDao().getAllRecipeData();
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        Recipe recipe = null;
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, RecipeIngredientWidget.class));
        // Update all available widget
        if (recipeData != null && recipeData.size() > 0) {
            recipe = recipeData.get(recipeData.size() - 1);
        }
        RecipeIngredientWidget.updateRecipeWidget(context, appWidgetManager, appWidgetIds, recipe);

    }

}
