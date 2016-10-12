package com.oleske.recipe;

import javax.persistence.Embeddable;

@Embeddable
public class Ingredient {
    private String name;
    private IngredientCategory category;

    public Ingredient() {
    }

    public Ingredient(String name, IngredientCategory category) {
        this.name = name;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public IngredientCategory getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "name='" + name + '\'' +
                ", category=" + category +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ingredient that = (Ingredient) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return category == that.category;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (category != null ? category.hashCode() : 0);
        return result;
    }
}
