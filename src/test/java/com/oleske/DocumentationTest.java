package com.oleske;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oleske.recipe.Ingredient;
import com.oleske.recipe.IngredientCategory;
import com.oleske.recipe.Recipe;
import com.oleske.restaurant.Restaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static java.util.Collections.singletonList;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.restdocs.snippet.Attributes.attributes;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public class DocumentationTest {
    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp(RestDocumentationContextProvider restDocumentation) {
        objectMapper = new ObjectMapper();

        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(documentationConfiguration(restDocumentation)
                        .uris()
                        .withScheme("https")
                        .withHost("restaurant-java.cfapps.pez.pivotal.io")
                        .withPort(443))
                .build();
    }

    @Test
    public void recipeController() throws Exception {
        Recipe request = new Recipe(
                null,
                singletonList(new Ingredient("Milk", IngredientCategory.DAIRY)),
                "Swedish Chef"
        );

        mockMvc.perform(post("/newRecipe")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andDo(document("newRecipe",
                        requestFields(
                                attributes(key("title").value("Fields for a New Recipe")),
                                fieldWithPath("id").ignored(),
                                fieldWithPath("ingredients").description("List of ingredients"),
                                fieldWithPath("ingredients[].name").description("Name of ingredient"),
                                fieldWithPath("ingredients[].category").description("Category of ingredient"),
                                fieldWithPath("chef").description("Name of the chef")
                        ),
                        responseFields(
                                fieldWithPath("id").description("Id of saved of recipe"),
                                fieldWithPath("ingredients").description("List of ingredients"),
                                fieldWithPath("ingredients[].name").description("Name of ingredient"),
                                fieldWithPath("ingredients[].category").description("Category of ingredient"),
                                fieldWithPath("chef").description("Name of the chef")
                        )
                ));

        mockMvc.perform(get("/recipeHasDairy?id=1"))
                .andExpect(status().isOk())
                .andDo(document("recipeHasDairy",
                        requestParameters(
                                parameterWithName("id").description("Id of recipe")
                        ),
                        responseFields(
                                fieldWithPath("hasDairy").description("Boolean if recipe of Id passed has dairy")
                        )
                ));
    }

    @Test
    public void restaurantController_post() throws Exception {
        Restaurant request = new Restaurant(
                null,
                "Swedish Food Company",
                "Kermit the Frog",
                "The Swedish Chef",
                "Swedish",
                "Zee best Svedeesh restoorunt! Bork Bork Bork!",
                "Ve-a serfe-a ell types ooff meetbells tu pleese-a yuoor pelete-a! Nutheeng is tuu guud fur yuoo! Bork Bork Bork!",
                "https://swedishfoodcompany.com",
                90,
                7,
                5
        );

        mockMvc.perform(post("/newRestaurant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andDo(document("newRestaurant",
                        requestFields(
                                attributes(key("title").value("Fields for a New Restaurant")),
                                fieldWithPath("id").ignored(),
                                fieldWithPath("name").description("Name of Restaurant"),
                                fieldWithPath("ownerName").description("Name of Owner"),
                                fieldWithPath("headChefName").description("Name of Head Chef"),
                                fieldWithPath("cuisineType").description("Type of Cuisine Served"),
                                fieldWithPath("shortDescription").description("Short Description of Restaurant"),
                                fieldWithPath("fullDescription").description("Long Description of Restaurant"),
                                fieldWithPath("websiteUrl").description("Website of Restaurant"),
                                fieldWithPath("rating").description("Restaurant Rating"),
                                fieldWithPath("michelinStarRating").description("Michelin Rating"),
                                fieldWithPath("zagatRating").description("Zagat Rating")
                        ),
                        responseFields(
                                fieldWithPath("id").description("Id of saved of restaurant"),
                                fieldWithPath("name").description("Name of Restaurant"),
                                fieldWithPath("ownerName").description("Name of Owner"),
                                fieldWithPath("headChefName").description("Name of Head Chef"),
                                fieldWithPath("cuisineType").description("Type of Cuisine Served"),
                                fieldWithPath("shortDescription").description("Short Description of Restaurant"),
                                fieldWithPath("fullDescription").description("Long Description of Restaurant"),
                                fieldWithPath("websiteUrl").description("Website of Restaurant"),
                                fieldWithPath("rating").description("Restaurant Rating"),
                                fieldWithPath("michelinStarRating").description("Michelin Rating"),
                                fieldWithPath("zagatRating").description("Zagat Rating")
                        )
                ));
    }

    @Test
    void restaurantController_get() throws Exception {
        mockMvc.perform(get("/restaurant"))
                .andExpect(status().isOk())
                .andDo(document("getRestaurant",
                        responseFields(
                                fieldWithPath("[]").description("An array of restaurants"),
                                fieldWithPath("[].id").description("Id of saved of restaurant"),
                                fieldWithPath("[].name").description("Name of Restaurant"),
                                fieldWithPath("[].ownerName").description("Name of Owner"),
                                fieldWithPath("[].headChefName").description("Name of Head Chef"),
                                fieldWithPath("[].cuisineType").description("Type of Cuisine Served"),
                                fieldWithPath("[].shortDescription").description("Short Description of Restaurant"),
                                fieldWithPath("[].fullDescription").description("Long Description of Restaurant"),
                                fieldWithPath("[].websiteUrl").description("Website of Restaurant"),
                                fieldWithPath("[].rating").description("Restaurant Rating"),
                                fieldWithPath("[].michelinStarRating").description("Michelin Rating"),
                                fieldWithPath("[].zagatRating").description("Zagat Rating")
                        )
                ));
    }
}
