package com.oleske.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oleske.restaurant.Restaurant;
import com.oleske.restaurant.RestaurantController;
import com.oleske.restaurant.RestaurantDto;
import com.oleske.restaurant.RestaurantRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class RestaurantControllerTest {
    private MockMvc mvc;
    private ObjectMapper objectMapper;
    @Mock
    private RestaurantRepository mockRestaurantRepository;

    @Before
    public void setUp() throws Exception {
        objectMapper = new ObjectMapper();
        mvc = MockMvcBuilders
                .standaloneSetup(new RestaurantController(mockRestaurantRepository))
                .build();
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
                .andExpect(jsonPath("$.zagatRating").value(request.getZagatRating()))
        ;
        verify(mockRestaurantRepository).save(request);
    }

}