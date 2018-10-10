package com.tourenathan.bakingapp.bakingapp.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tourenathan.bakingapp.bakingapp.R;
import com.tourenathan.bakingapp.bakingapp.model.Ingredient;

import java.util.List;
import java.util.Locale;

public class RecipeIngredientAdapter extends RecyclerView.Adapter<RecipeIngredientAdapter.ViewHolder> {

    private List<Ingredient> ingredients;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(R.layout.recipe_ingredient_item, parent, shouldAttachToParentImmediately);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ingredient ingredient = ingredients.get(position);
        holder.ingredientName.setText(ingredient.getIngredient());
        holder.ingredientQuantity.setText(String.format(Locale.ENGLISH,"%.1f %s", ingredient.getQuantity(), ingredient.getMeasure()));

    }

    @Override
    public int getItemCount() {
        return (ingredients != null) ? ingredients.size() : 0;
    }

    public void setData(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView ingredientName, ingredientQuantity;

        public ViewHolder(View itemView) {
            super(itemView);
            ingredientName = itemView.findViewById(R.id.ingredient_name);
            ingredientQuantity = itemView.findViewById(R.id.ingredient_quantity);
        }

    }
}
