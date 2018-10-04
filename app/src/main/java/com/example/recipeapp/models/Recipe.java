package com.example.recipeapp.models;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Recipe {
    @SerializedName("id")
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ingredients> getIngrediList() {
        return ingrediList;
    }

    public void setIngrediList(List<Ingredients> ingrediList) {
        this.ingrediList = ingrediList;
    }

    public List<Steps> getStepList() {
        return stepList;
    }

    public void setStepList(List<Steps> stepList) {
        this.stepList = stepList;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @SerializedName("name")
    private String name;
    @SerializedName("ingredients")
    private List<Ingredients> ingrediList;
    @SerializedName("steps")
    private  List<Steps>  stepList;
    @SerializedName("servings")
    private int servings;
    @SerializedName("image")
    private String image;





}
