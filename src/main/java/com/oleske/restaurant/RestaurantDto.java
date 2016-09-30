package com.oleske.restaurant;

public class RestaurantDto {
    private String name;
    private String headChefName;
    private String cusineType;
    private String shortDescription;
    private String fullDescription;
    private String websiteUrl;
    private int rating;
    private int michelinStarRating;
    private int zagatRating;

    public RestaurantDto(String name, String headChefName, String cusineType, String shortDescription, String fullDescription, String websiteUrl, int rating, int michelinStarRating, int zagatRating) {
        this.name = name;
        this.headChefName = headChefName;
        this.cusineType = cusineType;
        this.shortDescription = shortDescription;
        this.fullDescription = fullDescription;
        this.websiteUrl = websiteUrl;
        this.rating = rating;
        this.michelinStarRating = michelinStarRating;
        this.zagatRating = zagatRating;
    }

    public String getName() {
        return name;
    }

    public String getHeadChefName() {
        return headChefName;
    }

    public String getCusineType() {
        return cusineType;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getFullDescription() {
        return fullDescription;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public int getRating() {
        return rating;
    }

    public int getMichelinStarRating() {
        return michelinStarRating;
    }

    public int getZagatRating() {
        return zagatRating;
    }
}
