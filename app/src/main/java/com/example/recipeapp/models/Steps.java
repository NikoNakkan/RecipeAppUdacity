package com.example.recipeapp.models;
import com.google.gson.annotations.SerializedName;

public class Steps {
     @SerializedName("shortDescription")
     private String shortDescription;

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    @SerializedName("description")
     private String description;
     @SerializedName("videoURL")
     private String videoUrl;
     @SerializedName("thumbnailURL")
     private String thumbnailUrl;


}
