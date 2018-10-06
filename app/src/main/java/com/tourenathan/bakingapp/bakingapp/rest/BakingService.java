package com.tourenathan.bakingapp.bakingapp.rest;

import com.tourenathan.bakingapp.bakingapp.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface BakingService {

    @GET("baking.json")
    Call<List<Recipe>> getRecipe();
}
