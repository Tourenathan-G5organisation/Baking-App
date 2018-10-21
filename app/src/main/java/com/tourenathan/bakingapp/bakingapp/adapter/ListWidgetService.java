package com.tourenathan.bakingapp.bakingapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tourenathan.bakingapp.bakingapp.R;
import com.tourenathan.bakingapp.bakingapp.model.Ingredient;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        List<Ingredient> ingredientList = new ArrayList<>();
        if (intent.hasExtra(Intent.EXTRA_TEXT)) {
            String jsonText = intent.getStringExtra(Intent.EXTRA_TEXT);
            Gson gson = new Gson();
            Type ingredientListType = new TypeToken<List<Ingredient>>() {
            }.getType();
            ingredientList = gson.fromJson(jsonText, ingredientListType);
        }
        return new ListviewWidgetRemoteFactory(this.getApplicationContext(), ingredientList);
    }
}

class ListviewWidgetRemoteFactory implements RemoteViewsService.RemoteViewsFactory {
    static final String TAG = ListviewWidgetRemoteFactory.class.getSimpleName();

    private List<Ingredient> mIngredientList;
    private Context mContext;

    public ListviewWidgetRemoteFactory(Context context, List<Ingredient> ingredientList) {
        mIngredientList = ingredientList;
        mContext = context;
    }


    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {
        mIngredientList = null;
    }

    @Override
    public int getCount() {
        return (mIngredientList != null) ? mIngredientList.size() : 0;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews ingredientTextView = new RemoteViews(mContext.getPackageName(), R.layout.list_view_item);
        // Get the data item for this position
        Ingredient ingredient = mIngredientList.get(position);
        // Populate the data into the template view using the data object
        ingredientTextView.setTextViewText(R.id.label, String.format(Locale.ENGLISH, "%s %.1f %s", ingredient.getIngredient(), ingredient.getQuantity(), ingredient.getMeasure()));

        // Return the completed view to render on screen
        return ingredientTextView;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
