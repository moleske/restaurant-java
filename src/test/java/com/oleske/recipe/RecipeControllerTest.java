package com.oleske.recipe;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class RecipeControllerTest {
    private MockMvc mvc;
    private ObjectMapper objectMapper;
    @Mock
    private RecipeRepository mockRecipeRepository;

    @Before
    public void setUp() throws Exception {
        objectMapper = new ObjectMapper();
        mvc = MockMvcBuilders
                .standaloneSetup(new RecipeController(mockRecipeRepository))
                .build();
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
}