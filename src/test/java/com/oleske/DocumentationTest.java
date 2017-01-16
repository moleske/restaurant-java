package com.oleske;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oleske.recipe.Ingredient;
import com.oleske.recipe.IngredientCategory;
import com.oleske.recipe.Recipe;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.context.junit4.SpringRunner;
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

@RunWith(SpringRunner.class)
@SpringBootTest
public class DocumentationTest {
    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("build/generated-snippets");

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Before
    public void setUp() throws Exception {
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
}
