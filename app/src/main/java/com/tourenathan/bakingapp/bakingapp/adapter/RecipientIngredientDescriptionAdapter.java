package com.tourenathan.bakingapp.bakingapp.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tourenathan.bakingapp.bakingapp.R;

public class RecipientIngredientDescriptionAdapter extends RecyclerView.Adapter<RecipientIngredientDescriptionAdapter.ViewHolder> {

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView recipeDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeDescription = itemView.findViewById(R.id.recipe_description);
        }
    }
}
