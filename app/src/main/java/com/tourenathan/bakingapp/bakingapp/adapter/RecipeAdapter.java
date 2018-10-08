package com.tourenathan.bakingapp.bakingapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tourenathan.bakingapp.bakingapp.R;
import com.tourenathan.bakingapp.bakingapp.model.Recipe;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    public final String TAG = RecipeAdapter.class.getSimpleName();

    private List<Recipe> mRecipeList;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        int layoutIdForItem = R.layout.recipe_list_item;
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForItem, viewGroup, shouldAttachToParentImmediately);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Recipe recipe = mRecipeList.get(i);
        viewHolder.recipeName.setText((CharSequence) recipe.getName());
    }

    @Override
    public int getItemCount() {
        return (mRecipeList != null) ? mRecipeList.size() : 0;
    }

    // Update the recipe data
    public void setData(List<Recipe> data) {
        mRecipeList = data;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView recipeName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeName = itemView.findViewById(R.id.recipe_name);
        }
    }
}
