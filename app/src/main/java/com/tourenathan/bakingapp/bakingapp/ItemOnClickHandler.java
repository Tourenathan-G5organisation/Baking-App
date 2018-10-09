package com.tourenathan.bakingapp.bakingapp;

import com.tourenathan.bakingapp.bakingapp.model.Recipe;

public interface ItemOnClickHandler {

    /**
     * Onclick method use to capture the event
     *
     * @param recipe
     */
    void onClick(Recipe recipe);
}
