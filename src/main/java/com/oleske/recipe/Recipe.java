package com.oleske.recipe;

import javax.persistence.*;
import java.util.List;

@Entity
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ElementCollection
    private List<Ingredient> ingredients;
    private String chef;

    public Recipe() {
    }

    public Recipe(List<Ingredient> ingredients, String chef) {
        this.ingredients = ingredients;
        this.chef = chef;
    }

    public Long getId() {
        return id;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public String getChef() {
        return chef;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", ingredients=" + ingredients +
                ", chef='" + chef + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Recipe recipe = (Recipe) o;

        if (id != null ? !id.equals(recipe.id) : recipe.id != null) return false;
        if (ingredients != null ? !ingredients.equals(recipe.ingredients) : recipe.ingredients != null) return false;
        return chef != null ? chef.equals(recipe.chef) : recipe.chef == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (ingredients != null ? ingredients.hashCode() : 0);
        result = 31 * result + (chef != null ? chef.hashCode() : 0);
        return result;
    }
}
