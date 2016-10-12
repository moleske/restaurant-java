package com.oleske.recipe;

public enum IngredientCategory {
    DRY_GOOD, DAIRY, NUT;

    public boolean signifcantAmount() {
        return true;
    }
}
