package com.oleske.recipe;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RecipeController.class)
public class RecipeControllerTest {
    @Autowired
    private MockMvc mvc;
    private ObjectMapper objectMapper;
    @MockitoBean
    private RecipeRepository mockRecipeRepository;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void createRecipe_returns201() throws Exception {
        Recipe request = new Recipe(
                null,
                singletonList(new Ingredient("Milk", IngredientCategory.DAIRY)),
                "Swedish Chef"
        );

        Recipe recipe = new Recipe(
                1L,
                singletonList(new Ingredient("Milk", IngredientCategory.DAIRY)),
                "Swedish Chef"
        );

        when(mockRecipeRepository.save(any(Recipe.class))).thenReturn(recipe);

        mvc.perform(post("/newRecipe")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.ingredients", hasSize(1)))
                .andExpect(jsonPath("$.ingredients[0].name").value("Milk"))
                .andExpect(jsonPath("$.ingredients[0].category").value(IngredientCategory.DAIRY.toString()))
                .andExpect(jsonPath("$.chef").value("Swedish Chef"));
        verify(mockRecipeRepository).save(request);
    }

    @Test
    public void recipeContainsSignificantAmountOfDairy_whenRecipeIsNull_returnsFalse() throws Exception {
        when(mockRecipeRepository.findById(anyLong())).thenReturn(Optional.empty());
        mvc.perform(get("/recipeHasDairy?id=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("hasDairy").value("false"));
        verify(mockRecipeRepository).findById(1L);
    }

    @Test
    public void recipeContainsSignificantAmountOfDairy_whenRecipeHasDairyIngredient_returnsTrue() throws Exception {
        Recipe recipe = new Recipe(
                1L,
                singletonList(new Ingredient("Milk", IngredientCategory.DAIRY)),
                "Swedish Chef"
        );
        when(mockRecipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));
        mvc.perform(get("/recipeHasDairy?id=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("hasDairy").value("true"));
        verify(mockRecipeRepository).findById(1L);
    }

    @Test
    public void recipeContainsSignificantAmountOfDairy_whenRecipeNoHasDairyIngredient_returnsFalse() throws Exception {
        Recipe recipe = new Recipe(
                1L,
                singletonList(new Ingredient("Not Milk", IngredientCategory.NUT)),
                "Swedish Chef"
        );
        when(mockRecipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));
        mvc.perform(get("/recipeHasDairy?id=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("hasDairy").value("false"));
        verify(mockRecipeRepository).findById(1L);
    }

    @Test
    public void recipeContainsSignificantAmountOfDairy_whenRecipeHasNullIngredient_returnsFalse() throws Exception {
        Recipe recipe = new Recipe(
                1L,
                singletonList(new Ingredient("Not Milk", null)),
                "Swedish Chef"
        );
        when(mockRecipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));
        mvc.perform(get("/recipeHasDairy?id=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("hasDairy").value("false"));
        verify(mockRecipeRepository).findById(1L);
    }

}