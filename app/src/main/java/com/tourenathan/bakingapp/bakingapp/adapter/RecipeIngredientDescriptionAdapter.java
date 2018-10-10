package com.tourenathan.bakingapp.bakingapp.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.tourenathan.bakingapp.bakingapp.OnDescriptionIngredientItemClickHandler;
import com.tourenathan.bakingapp.bakingapp.R;
import com.tourenathan.bakingapp.bakingapp.model.Ingredient;
import com.tourenathan.bakingapp.bakingapp.model.Recipe;
import com.tourenathan.bakingapp.bakingapp.model.Step;

import java.util.List;

public class RecipeIngredientDescriptionAdapter extends RecyclerView.Adapter<RecipeIngredientDescriptionAdapter.ViewHolder> {

    public static final int RECIPE_INGREDIENT_TYPE = 1;
    public static final int RECIPE_DESCRIPTION_TYPE = 2;

    private Recipe recipe;
    private List<Step> recipeStepList;
    private List<Ingredient> recipeIngredientList;

    OnDescriptionIngredientItemClickHandler mClickHandler;

    public RecipeIngredientDescriptionAdapter(OnDescriptionIngredientItemClickHandler onClickHandler) {
        mClickHandler = onClickHandler;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(R.layout.recipe_description_list_item, parent, shouldAttachToParentImmediately);
        return new ViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return (position == 0 && (recipeIngredientList.size() > 0)) ? RECIPE_INGREDIENT_TYPE : RECIPE_DESCRIPTION_TYPE;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (getItemViewType(position) == RECIPE_INGREDIENT_TYPE) {
            holder.recipeDescription.setText(R.string.recipe_ingredients);
        } else {
            if (recipeIngredientList.size() == 0) {
                holder.recipeDescription.setText(recipe.getSteps().get(position).getShortDescription());
            } else {
                holder.recipeDescription.setText(recipe.getSteps().get(position - 1).getShortDescription());
            }

        }
    }

    @Override
    public int getItemCount() {
        if (recipe != null) {
            return (recipeIngredientList.size() > 0) ? recipeStepList.size() + 1 : recipeStepList.size();
        }
        return 0;
    }

    /**
     * Set the recipe which data will be displayed
     *
     * @param recipe
     */
    public void setData(Recipe recipe) {
        this.recipe = recipe;
        recipeIngredientList = recipe.getIngredients();
        recipeStepList = recipe.getSteps();
        notifyDataSetChanged();

    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView recipeDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeDescription = itemView.findViewById(R.id.recipe_description);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Gson gson = new Gson();
            int itemviewType = getItemViewType();
            if (getItemViewType() == RECIPE_INGREDIENT_TYPE){
                mClickHandler.onClick(gson.toJson(recipe.getIngredients()),itemviewType);
            }
            else {
                if (recipeIngredientList.size() == 0) {
                    mClickHandler.onClick(gson.toJson(recipe.getSteps().get(position)), itemviewType);
                }
                else {
                    mClickHandler.onClick(gson.toJson(recipe.getSteps().get(position-1)), itemviewType);
                }

            }

        }
    }
}
