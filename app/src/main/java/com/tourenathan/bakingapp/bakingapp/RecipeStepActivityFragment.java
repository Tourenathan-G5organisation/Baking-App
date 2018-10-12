package com.tourenathan.bakingapp.bakingapp;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tourenathan.bakingapp.bakingapp.model.Step;

/**
 * A placeholder fragment containing a simple view.
 */
public class RecipeStepActivityFragment extends Fragment {

    public static final String TAG = RecipeStepActivityFragment.class.getSimpleName();

    Step mStep;
    TextView mDescription;

    public RecipeStepActivityFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_step, container, false);

        mDescription = rootView.findViewById(R.id.step_description_Textview);

        return rootView;
    }

    /**
     * Set the recipe step to display in fragment
     *
     * @param recipeStep Step to display
     */

    public void setStep(Step recipeStep) {
        mStep = recipeStep;
    }

    public void initialiseData() {
        if (mStep != null) {
            mDescription.setText(mStep.getDescription());
        }
    }
}
