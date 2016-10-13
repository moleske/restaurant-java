package com.oleske.recipe;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RecipeController {

    private RecipeRepository recipeRepository;

    public RecipeController(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @PostMapping("/newRecipe")
    public ResponseEntity<Recipe> createRecipe(@RequestBody Recipe recipe) {
        return new ResponseEntity<>(recipeRepository.save(recipe), HttpStatus.CREATED);
    }

    @GetMapping("/recipeHasDairy")
    public ResponseEntity<Boolean> recipeContainsSignificantAmountOfDairy(@RequestParam Long id) {
        Recipe recipe = recipeRepository.findOne(id);
        if (recipe != null) {
            boolean result = recipe.getIngredients()
                    .stream().anyMatch(ingredient ->
                            ingredient.getCategory() != null && ingredient.getCategory().equals(IngredientCategory.DAIRY));
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>(false, HttpStatus.OK);
    }
}
