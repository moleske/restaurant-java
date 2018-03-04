package com.oleske.restaurant;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(RestaurantController.class)
public class RestaurantControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private RestaurantRepository mockRestaurantRepository;

    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void createRestaurant_returns201() throws Exception {
        Restaurant request = new Restaurant(
                null,
                "name",
                "ownerName",
                "headChefName",
                "cuisineType",
                "shortDescription",
                "fullDescription",
                "websiteUrl",
                0,
                1,
                2
        );

        Restaurant restaurant = new Restaurant(
                1L,
                "name",
                "ownerName",
                "headChefName",
                "cuisineType",
                "shortDescription",
                "fullDescription",
                "websiteUrl",
                0,
                1,
                2
        );

        when(mockRestaurantRepository.save(any(Restaurant.class))).thenReturn(restaurant);

        mvc.perform(post("/newRestaurant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value(request.getName()))
                .andExpect(jsonPath("$.ownerName").value(request.getOwnerName()))
                .andExpect(jsonPath("$.headChefName").value(request.getHeadChefName()))
                .andExpect(jsonPath("$.cuisineType").value(request.getCuisineType()))
                .andExpect(jsonPath("$.shortDescription").value(request.getShortDescription()))
                .andExpect(jsonPath("$.fullDescription").value(request.getFullDescription()))
                .andExpect(jsonPath("$.websiteUrl").value(request.getWebsiteUrl()))
                .andExpect(jsonPath("$.rating").value(request.getRating()))
                .andExpect(jsonPath("$.michelinStarRating").value(request.getMichelinStarRating()))
                .andExpect(jsonPath("$.zagatRating").value(request.getZagatRating()));
        verify(mockRestaurantRepository).save(request);
    }

}