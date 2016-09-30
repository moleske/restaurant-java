package com.oleske.restaurant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String ownerName;
    private String headChefName;
    private String cuisineType;
    private String shortDescription;
    private String fullDescription;
    private String websiteUrl;
    private int rating;
    private int michelinStarRating;
    private int zagatRating;

    private Restaurant() {
    }

    public Restaurant(Long id, String name, String ownerName, String headChefName, String cusineType, String shortDescription, String fullDescription, String websiteUrl, int rating, int michelinStarRating, int zagatRating) {
        this.id = id;
        this.name = name;
        this.ownerName = ownerName;
        this.headChefName = headChefName;
        this.cuisineType = cusineType;
        this.shortDescription = shortDescription;
        this.fullDescription = fullDescription;
        this.websiteUrl = websiteUrl;
        this.rating = rating;
        this.michelinStarRating = michelinStarRating;
        this.zagatRating = zagatRating;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getHeadChefName() {
        return headChefName;
    }

    public String getCuisineType() {
        return cuisineType;
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

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ownerName='" + ownerName + '\'' +
                ", headChefName='" + headChefName + '\'' +
                ", cuisineType='" + cuisineType + '\'' +
                ", shortDescription='" + shortDescription + '\'' +
                ", fullDescription='" + fullDescription + '\'' +
                ", websiteUrl='" + websiteUrl + '\'' +
                ", rating=" + rating +
                ", michelinStarRating=" + michelinStarRating +
                ", zagatRating=" + zagatRating +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Restaurant that = (Restaurant) o;

        if (rating != that.rating) return false;
        if (michelinStarRating != that.michelinStarRating) return false;
        if (zagatRating != that.zagatRating) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (ownerName != null ? !ownerName.equals(that.ownerName) : that.ownerName != null) return false;
        if (headChefName != null ? !headChefName.equals(that.headChefName) : that.headChefName != null) return false;
        if (cuisineType != null ? !cuisineType.equals(that.cuisineType) : that.cuisineType != null) return false;
        if (shortDescription != null ? !shortDescription.equals(that.shortDescription) : that.shortDescription != null)
            return false;
        if (fullDescription != null ? !fullDescription.equals(that.fullDescription) : that.fullDescription != null)
            return false;
        return websiteUrl != null ? websiteUrl.equals(that.websiteUrl) : that.websiteUrl == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (ownerName != null ? ownerName.hashCode() : 0);
        result = 31 * result + (headChefName != null ? headChefName.hashCode() : 0);
        result = 31 * result + (cuisineType != null ? cuisineType.hashCode() : 0);
        result = 31 * result + (shortDescription != null ? shortDescription.hashCode() : 0);
        result = 31 * result + (fullDescription != null ? fullDescription.hashCode() : 0);
        result = 31 * result + (websiteUrl != null ? websiteUrl.hashCode() : 0);
        result = 31 * result + rating;
        result = 31 * result + michelinStarRating;
        result = 31 * result + zagatRating;
        return result;
    }
}
